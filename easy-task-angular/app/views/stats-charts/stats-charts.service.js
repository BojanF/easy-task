/**
 * Created by Bojan on 8/14/2017.
 */


(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('StatsChartsService', StatsChartsServiceFn);

  StatsChartsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function StatsChartsServiceFn($log, $resource) {


    var administratingProjectsResource = $resource('http://localhost:8000/api/user/administrating-projects/:id', {}, {});
    var tasksStats = $resource('http://localhost:8000/api/user/tasks-stats/:id', {}, {});

    var service = {

      getAdministratingProjects: getAdministratingProjectsFn,
      getTaskStatesForProjects: getTaskStatesForProjectsFn
    };
    return service;



    function getAdministratingProjectsFn(userId){
      return administratingProjectsResource.query({id:userId}).$promise;
    }

    function getTaskStatesForProjectsFn(userId){
      return tasksStats.query({id:userId}).$promise;
    }


  }

})(angular);

