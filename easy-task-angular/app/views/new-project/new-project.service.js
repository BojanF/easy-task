/**
 * Created by Bojan on 8/14/2017.
 */


(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('NewProjectService', NewProjectServiceFn);

  NewProjectServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function NewProjectServiceFn($log, $resource) {

    var insertProjectResource = $resource("http://localhost:8000/api/project/:id", {}, {});
    var getTeamsResource = $resource("http://localhost:8000/api/user/led-teams/:id", {}, {});

    var service = {
      saveNewProject: saveNewProjectFn,
      getTeamsLedByUser: getTeamsLedByUserFn
    };
    return service;

    function saveNewProjectFn(newProject){
      return insertProjectResource.save(newProject).$promise;
    }

    function getTeamsLedByUserFn(userId){
      return getTeamsResource.query({id:userId}).$promise;
    }


  }

})(angular);
