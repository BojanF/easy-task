/**
 * Created by Bojan on 8/11/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('YourProjectsService', YourProjectsServiceFn);

  YourProjectsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function YourProjectsServiceFn($log, $resource) {


    var yourProjectsResource = $resource('http://localhost:8000/api/user/your-projects/:id', {}, {});

    var service = {
      getProjectThatUserWorksOn: getProjectThatUserWorksOnFn
    };
    return service;

    function getProjectThatUserWorksOnFn(userId){
      return yourProjectsResource.query({id:userId}).$promise;
    }

  }

})(angular);
