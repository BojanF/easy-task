/**
 * Created by Bojan on 8/15/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('NewTeamService', NewTeamServiceFn);

  NewTeamServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function NewTeamServiceFn($log, $resource) {

    var saveTeamResource = $resource("http://localhost:8000/api/team/:id", {}, {});
    var getCoworkersResource = $resource("http://localhost:8000/api/coworkers/get-coworkers/:id", {}, {});
    var getLeaderResource = $resource("http://localhost:8000/api/leader/get-leader/:id", {}, {});
    var getUserResource = $resource("http://localhost:8000/api/user/:id");
    var saveNewLeader = $resource("http://localhost:8000/api/leader/:id", {}, {});

    var service = {
      saveNewTeam: saveNewTeamFn,
      getCoworkers: getCoworkersFn,
      getLeader: getLeaderFn,
      getUser: getUserFn,
      saveNewLeader: saveNewLeaderFn
    };
    return service;

    function saveNewTeamFn(newProject){
      return saveTeamResource.save(newProject).$promise;
    }

    function getCoworkersFn(userId){
      return getCoworkersResource.query({id:userId}).$promise;
    }

    function getLeaderFn(userId){
      return getLeaderResource.get({id:userId}).$promise;
    }

    function getUserFn(userId){
      return getUserResource.get({id:userId}).$promise;
    }

    function saveNewLeaderFn(newLeader){
      return saveNewLeader.save(newLeader).$promise;
    }

  }

})(angular);
