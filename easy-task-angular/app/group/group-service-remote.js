/**
 * Created by Bojan on 12/2/2016.
 */
(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('GroupService', GroupServiceFn);

  GroupServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function GroupServiceFn($log, $resource) {
    var groupsList = [];
    var groupIdSequence = 0;
    var resource = $resource('http://localhost:8000/api/groups/:id', {}, {
      update:{isArray:false, method:'PUT'}
    });
    var service = {
      save: saveFn,
      update: updateFn,
      getById: getByIdFn,
      getAll: getAllFn,
      remove: removeFn
    };


    return service;


    function saveFn(groupEntity) {
      if(groupEntity.id === undefined) {
        return resource.save(groupEntity, function(data){
          groupEntity.id=data.id;
        }).$promise;
      }
      return updateFn(groupEntity);


    }

    function updateFn(groupEntity) {
      if (groupEntity.id === undefined) {
        $log.debug("IFFFF");
        return saveFn(groupEntity).$promise;
      }
      $log.debug("UPDATE");
      return resource.update({id: groupEntity.id}, groupEntity).$promise;

    }

    function getByIdFn(groupId) {
      return resource.get({id:groupId}).$promise;

    }

    function getAllFn() {
      return resource.query().$promise;

    }

    function removeFn(groupEntity) {
        $log.debug("DELETE");
        return resource.delete({id:groupEntity.id}).$promise;
    }
  }

})(angular);

