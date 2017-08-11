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


    var administratingProjectsResource = $resource('http://localhost:8000/api/user/administrating-projects/:id', {}, {});

    var service = {
      getAdministratingProjects: getAdministratingProjectsFn,
    };
    return service;

    function getAdministratingProjectsFn(userId){
      return administratingProjectsResource.query({id:userId}).$promise;
    }

  }

})(angular);
