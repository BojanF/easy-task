/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('NewProjectsController', NewProjectsController);

  NewProjectsController.$inject = ['$log', '$scope', 'NewProjectService','$cookies','$location'];

  /* @ngInject */
  function NewProjectsController($log, $scope, NewProjectService,$cookies,$location) {
    var vm = this;
    //variables declaration
    vm.user = {};
    vm.newProject = {};
    // vm.test = [];
    vm.teamsLedByUser = [];

    vm.inputDates = {
      createdOn: null,
      deadline: null
    };

    vm.datesRestrictions = {
      createdOn:{
        min: moment(new Date())
      },
      deadline:{
        min:null
      }
    };
    vm.uiState = {
      errorNewProject: null,
      successNewProject: null,
      fetchingTeamsMessage: null,
      loadGif: true
    };

    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getTeamsLedByUser();
    }
    else {
      $location.path('/login');
    }

    //functions declaration
    vm.saveNewProject = saveNewProjectFn;
    vm.clearNewProject = clearNewProjectFn;

    //functions invocation


    //functions implementation
    function saveNewProjectFn() {

      $("#savingProject").show();

      vm.uiState.successNewProject = null;
      vm.uiState.errorNewProject = null;

      vm.newProject.createdOn = vm.inputDates.createdOn.toDate().getTime();
      vm.newProject.deadline = vm.inputDates.deadline.toDate().getTime();
      vm.newProject.state = 'CREATED';
      console.log(vm.newProject);
      NewProjectService.saveNewProject(vm.newProject).then(successCallbackNewProject, errorCallbackNewProject);

      function successCallbackNewProject(data){
        $("#savingProject").hide();
        vm.uiState.successNewProject = "Project with name \"" + data.name + "\" was successfully created!";
        clearNewProjectFn();
      }

      function errorCallbackNewProject(data){
        $("#savingProject").hide();
        vm.uiState.errorNewProject = "Project was not successfully created! Try again later!";
      }


    }

    function clearNewProjectFn() {
      vm.newProject = {};
      vm.inputDates = {
        createdOn: null,
        deadline: null
      };
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

    $scope.$watch('vm.inputDates.createdOn', function(){
      vm.datesRestrictions.deadline.min = moment(vm.inputDates.createdOn).add(3, 'h');
    }, true);


  }

})(angular);
