/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('NewProjectsController', NewProjectsController);

  NewProjectsController.$inject = ['$log', 'NewProjectService'];

  /* @ngInject */
  function NewProjectsController($log, NewProjectService) {
    var vm = this;
    //variables declaration
    vm.USER_ID = 1;
    vm.user = {};
    vm.newProject = {};
    // vm.test = [];
    vm.teamsLedByUser = [];

    vm.uiState = {
      errorNewProject: null,
      successNewProject: null,
      fetchingTeamsMessage: null,
      loadGif: true
    }

    //functions declaration
    vm.saveNewProject = saveNewProjectFn;
    vm.clearNewProject = clearNewProjectFn;

    //functions invocation
    getTeamsLedByUser();

    //functions implementation
    function saveNewProjectFn() {
      vm.uiState.successNewProject = null;
      vm.uiState.errorNewProject = null;

      vm.newProject.createdOn = vm.newProject.createdOn.toDate().getTime();
      vm.newProject.deadline = vm.newProject.deadline.toDate().getTime();
      vm.newProject.state = 'FINISHED';
      console.log(vm.newProject);
      NewProjectService.saveNewProject(vm.newProject).then(successCallbackNewProject, errorCallbackNewProject);

      function successCallbackNewProject(data){
        vm.uiState.successNewProject = "Project with name \"" + data.name + "\" was successfully created!"
        clearNewProjectFn();
      }

      function errorCallbackNewProject(data){
        vm.uiState.errorNewProject = "Project was not successfully created! Try again later!";
      }


    }

    function clearNewProjectFn() {
      vm.newProject = {};
    }

    //helper functions
    function getTeamsLedByUser(){
      NewProjectService.getTeamsLedByUser(vm.USER_ID).then(successCallbackTeam, errorCallbackTeam);

      function successCallbackTeam(data){
        vm.teamsLedByUser = data;
        console.log(vm.teamsLedByUser);
        vm.uiState.loadGif = false;
        if(vm.teamsLedByUser.length == 0){
          vm.uiState.fetchingTeamsMessage = "You are not leader to any team";
        }
      }

      function errorCallbackTeam(data){
        console.log("Something went wrong");
        vm.uiState.fetchingTeamsMessage = "Something went wrong! Try again later!";
        vm.uiState.loadGif = false;
      }
    }


  }

})(angular);
