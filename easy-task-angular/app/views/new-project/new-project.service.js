/**
 * Created by Bojan on 8/14/2017.
 */


(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .factory('NewProjectService', NewProjectServiceFn);

  NewProjectServiceFn.$inject = ['$log', '$resource'];

  /* @ngInject */
  function NewProjectServiceFn($log, $resource) {

    var insertProjectResource = $resource("https://kostancev.com:8080/api/project/:id", {}, {});
    var getTeamsResource = $resource("https://kostancev.com:8080/api/user/led-teams/:id", {}, {});

    var service = {
      saveNewProject: saveNewProjectFn,
      getTeamsLedByUser: getTeamsLedByUserFn
    };
    return service;

    function saveNewProjectFn(newProject){
      return insertProjectResource.save(newProject).$promise;
    }

    function getTeamsLedByUserFn(userId){
      return getTeamsResource.query({id:userId}).$promise;
    }


  }

})(angular);
