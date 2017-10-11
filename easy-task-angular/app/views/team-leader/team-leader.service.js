/**
 * Created by Bojan on 8/14/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('TeamLeaderService', TeamLeaderServiceFn);

  TeamLeaderServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function TeamLeaderServiceFn($log, $resource) {

    var getLeaderResource = $resource("https://kostancev.com:8080/api/user/team-stats/:id", {}, {});
    var teamRepository = $resource("https://kostancev.com:8080/api/team/:id", {}, {});

    var service = {
      getTeamStats: getTeamStatsFn,
      deleteTeam: deleteTeamFn
    };
    return service;

    function getTeamStatsFn(userId){
      return getLeaderResource.query({id:userId}).$promise;
    }

    function deleteTeamFn(teamId){
      return teamRepository.delete({id:teamId}).$promise;
    }

  }

})(angular);
