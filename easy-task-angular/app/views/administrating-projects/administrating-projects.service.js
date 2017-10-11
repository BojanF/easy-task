/**
 * Created by Bojan on 8/11/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('AdministratingProjectsService', AdministratingProjectsServiceFn);

  AdministratingProjectsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function AdministratingProjectsServiceFn($log, $resource) {


    var administratingProjectsResource = $resource('https://kostancev.com:8080/api/user/administrating-projects/:id', {}, {});
    var projectRepository = $resource('https://kostancev.com:8080/api/project/:id', {}, {});

    var service = {
      getAdministratingProjects: getAdministratingProjectsFn,
      removeProject: removeProjectFn
    };
    return service;

    function getAdministratingProjectsFn(userId){
      return administratingProjectsResource.query({id:userId}).$promise;
    }

    function removeProjectFn(projectId){
      return projectRepository.delete({id:projectId}).$promise;
    }

  }

})(angular);
