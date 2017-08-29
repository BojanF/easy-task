/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TeamsWorkingOnController', TeamsWorkingOnController);

  TeamsWorkingOnController.$inject = ['$log', 'TeamsWorkingOnService'];

  /* @ngInject */
  function TeamsWorkingOnController($log, TeamsWorkingOnService) {
    var vm = this;

    //variables declaration
    vm.USER_ID = 1;
    vm.teams = [];
    vm.uiState={
      loadGif: true,
      showTeams: false,
      showNoTeamsPanel: false,
      showErrorPanel: false
    };

    //functions declaration


    //functions invocation
    getTeams();

    //functions implementation
    function getTeams(){
      TeamsWorkingOnService.getTeamsInfo(vm.USER_ID).then(successCallback, errorCallback);

      function successCallback(data){
        vm.teams = data;

        vm.uiState.loadGif = false;
        vm.uiState.showErrorPanel = false;
        if(vm.teams.length > 0){

          vm.uiState.showTeams = true;
          vm.uiState.showNoTeamsPanel = false;

        }
        else{
          vm.uiState.showTeams = false;
          vm.uiState.showNoTeamsPanel = true;
        }
      }

      function errorCallback(){
        vm.uiState.loadGif = false;
        vm.uiState.showTeams = false;
        vm.uiState.showNoTeamsPanel = false;
        vm.uiState.showErrorPanel = true;
      }
    }

    //helper functions

  }

})(angular);
