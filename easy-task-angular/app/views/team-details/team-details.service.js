/**
 * Created by Bojan on 8/28/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('TeamDetailsService', TeamDetailsServiceFn);

  TeamDetailsServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function TeamDetailsServiceFn($log, $resource) {

    var teamResource = $resource("https://kostancev.com:8080/api/team/:id", {}, {});
    var teamProjectsResource = $resource("https://kostancev.com:8080/api/team/team-projects/:id", {}, {});
    var getCoworkersResource = $resource("https://kostancev.com:8080/api/coworkers/get-coworkers/:id", {}, {});
    var updateTeamResource = $resource("https://kostancev.com:8080/api/team/update/:id", {}, {});
    var teamStatsResource = $resource("https://kostancev.com:8080/api/team/stats/:id", {}, {});

    var service = {
      getTeam: getTeamFn,
      getProjectsForTeam: getProjectsForTeamFn,
      getCoworkers: getCoworkersFn,
      updateTeam: updateTeamFn,
      getTeamStats: getTeamStatsFn

    };
    return service;

    function getTeamFn(teamId){
      return teamResource.get({id:teamId}).$promise;
    }

    function getProjectsForTeamFn(teamId){
      return teamProjectsResource.query({id:teamId}).$promise;
    }

    function getCoworkersFn(userId){
      return getCoworkersResource.query({id:userId}).$promise;
    }

    function updateTeamFn(team){
      return updateTeamResource.save(team).$promise;
    }

    function getTeamStatsFn(teamId){
      return teamStatsResource.query({id:teamId}).$promise;
    }

  }

})(angular);
