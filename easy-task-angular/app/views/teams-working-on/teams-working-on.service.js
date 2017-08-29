/**
 * Created by Bojan on 8/29/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('TeamsWorkingOnService', TeamsWorkingOnServiceFn);

  TeamsWorkingOnServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function TeamsWorkingOnServiceFn($log, $resource) {

    var getTeamsInfoResource = $resource("http://localhost:8000/api/user/teams-working-on/:id", {}, {});

    var service = {
      getTeamsInfo: getTeamsInfoFn
    };
    return service;

    function getTeamsInfoFn(userId){
      return getTeamsInfoResource.query({id:userId}).$promise;
    }

  }

})(angular);
