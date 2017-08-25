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

    var getLeaderResource = $resource("http://localhost:8000/api/user/team-stats/:id", {}, {});

    var service = {
      getTeamStats: getTeamStatsFn
    };
    return service;

    function getTeamStatsFn(userId){
      return getLeaderResource.query({id:userId}).$promise;
    }

  }

})(angular);
