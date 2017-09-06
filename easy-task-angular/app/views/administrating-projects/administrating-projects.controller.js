/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('AdministratingProjectsController', AdministratingProjectsController);

  AdministratingProjectsController.$inject = ['$log', 'AdministratingProjectsService'];

  /* @ngInject */
  function AdministratingProjectsController($log, AdministratingProjectsService) {
    var vm = this;
    //variables declaration
    vm.USER_ID = 1;
    vm.projects = [];
    vm.uiState = {
      loadGif: true,
      showProjects: false,
      showNoProjectsPanel: false,
      showErrorPanel: false,
      successDelete: null,
      errorDelete: null
    };

    //functions declaration
    vm.getAdministratingProjects = getAdministratingProjectsFn;
    vm.removeProject = removeProjectFn;

    //functions invocation
    getAdministratingProjectsFn();

    //functions implementation
    function getAdministratingProjectsFn(){

      vm.uiState.successDelete = null;
      vm.uiState.errorDelete = null;

      AdministratingProjectsService.getAdministratingProjects(vm.USER_ID).then(successCallback, errorCallback);

      function successCallback(data) {

        vm.projects = data;

        vm.uiState.loadGif = false;
        vm.uiState.showErrorPanel= false;
        if(vm.projects.length > 0){
          vm.uiState.showProjects = true;
          vm.uiState.showNoProjectsPanel = false;
          setProjectsCssClassAndDates(vm.projects);
        }
        else{
          vm.uiState.showProjects = false;
          vm.uiState.showNoProjectsPanel = true;
        }

      }

      function errorCallback(){
        vm.uiState.loadGif = false;
        vm.uiState.showProjects = false;
        vm.uiState.showNoProjectsPanel = false;
        vm.uiState.showErrorPanel = true;

      }
    }

    function removeProjectFn(projectId){

      vm.uiState.successDelete = null;
      vm.uiState.errorDelete = null;

      AdministratingProjectsService.removeProject(projectId).then(

        function(){
          //success callback
          console.log("Se izbrisa!!!");
          getAdministratingProjectsFn();
          vm.uiState.successDelete = "Project was successfully deleted!";
        },

        function(){
          //error callback
          console.log("ne se izbrisa meeeh :(")
          var button = $(".removeProject");
          setTimeout(function(){
            button.html('<i class="fa fa-times"></i>&nbsp; Delete project');
            button.prop('disabled',false);
          }, 300);

          vm.uiState.errorDelete = "Something happened. Try again later to delete the project!";
        }

      );

    }
    //helper functions
    function setProjectsCssClassAndDates(projects){

      var size = projects.length;

      for(var i=0 ; i<size ; i++){
        var currProject = projects[i];

        currProject.createdOn = dateMillisecondsToDate(currProject.createdOn);
        currProject.completedOn = dateMillisecondsToDate(currProject.completedOn);
        currProject.deadline = dateMillisecondsToDate(currProject.deadline);

        if(currProject.state == 'CREATED'){
          currProject.cssClass = 'label label-default';
        }
        else if(currProject.state == 'NOT_STARTED'){
          currProject.cssClass = 'label label-info';
          currProject.state = 'NOT STARTED';
        }
        else if(currProject.state == 'IN_PROGRESS'){
          currProject.cssClass = 'label label-warning';
          currProject.state = 'IN PROGRESS';
        }
        else if(currProject.state == 'UP_TO_DATE'){
          currProject.cssClass = 'label label-primary';
          currProject.state = 'UP TO DATE';
        }
        else if(currProject.state == 'FINISHED'){
          currProject.cssClass = 'label label-success';

        }
        else{
          currProject.cssClass = 'label label-danger';
          currProject.state = 'BREACH OF DEADLINE';
        }

        projects[i] = currProject;
      }
    }

    function dateMillisecondsToDate(milliseconds){
      if(milliseconds != null) {
        var d = new Date(milliseconds);
        var month = parseInt(d.getMonth()) + 1;
        var minutes = d.getMinutes();
        if (minutes.toString().length == 1) {
          minutes = '0' + minutes;
        }
        return d.getDate() + "." +
          month + "." +
          d.getFullYear() + " " +
          d.getHours() + ":" +
          minutes;
      }
      else{
        return "Not finished yet";
      }
    }
  }

})(angular);
