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


    var getTeamsResource = $resource("http://localhost:8000/api/user/led-teams/:id", {}, {});
    var getProjectsByTeamsResource = $resource("http://localhost:8000/api/user/projects-by-teams/:id", {}, {});

    var service = {

      getTeamsLedByUser: getTeamsLedByUserFn,
      getProjectsByTeams: getProjectsByTeamsFn
    };
    return service;

    function getTeamsLedByUserFn(userId){
      return getTeamsResource.query({id:userId}).$promise;
    }

    function getProjectsByTeamsFn(userId){
      return getProjectsByTeamsResource.get({id:userId}).$promise;
    }


  }

})(angular);
