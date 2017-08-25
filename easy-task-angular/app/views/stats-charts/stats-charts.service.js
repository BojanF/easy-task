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


    var projectStatsResource = $resource('http://localhost:8000/api/user/project-stats/:id', {}, {});
    var taskStatsResource = $resource('http://localhost:8000/api/user/task-stats/:id', {}, {});
    var getLeaderResource = $resource("http://localhost:8000/api/user/team-stats/:id", {}, {});

    var service = {

      getProjectStats: getProjectStatsFn,
      getTasksStats: getTaskStatsFn,
      getTeamStats: getTeamStatsFn

    };
    return service;



    function getProjectStatsFn(userId){
      return projectStatsResource.query({id:userId}).$promise;
    }

    function getTaskStatsFn(userId){
      return taskStatsResource.query({id:userId}).$promise;
    }

    function getTeamStatsFn(userId){
      return getLeaderResource.query({id:userId}).$promise;
    }


  }

})(angular);

