/**
 * Created by Bojan on 8/7/2017.
 */



(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('HomePageController', HomePageController);

  HomePageController.$inject = ['$log', '$scope', 'HomePageService','$cookies','$location'];

  /* @ngInject */
  function HomePageController($log, $scope, HomePageService,$cookies,$location) {
    var vm = this;
    vm.user = null;
    //variables declaration
    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getUserFn();
      $("#page-wrapper").show();
      getTasksStatesFn();
      getActiveTasksFn();
      getUrgentProjectsFn();
    }
    else {
      $location.path('/login');
    }


    vm.title = 'easy-task';
    vm.listTasksStates = null;
    vm.group = null
    vm.tasksStates = {
      notStarted: 0,
      inProgress: 0,
      finished: 0,
      breach: 0
    }
    vm.activeTasks = [];
    vm.urgentProjects = [];
    vm.uiState = {
      activeTasks:{
        loadGif: true,
        showTasks: false,
        showNoActiveTasksPanel: false,
        showErrorPanel: false
      },
      urgentProjects:{
        loadGif: true,
        showUrgentProjects: false,
        showNoUrgentProjectsPanel: false,
        showErrorPanel: false
      }
    }
    vm.dateSelected = "Enter date";


    //functions declaration
    vm.getUser = getUserFn;
    vm.getTasksStates = getTasksStatesFn;
    vm.getActiveTasks = getActiveTasksFn;
    vm.getUrgentProjects = getUrgentProjectsFn;

    //functions invocation









    //functions implementation

     function getUserFn(){
        HomePageService.getUser(vm.USER_ID).then(function (data) {
        vm.user = data;
       });
     }

    function getTasksStatesFn(){
      HomePageService.getTasksStates(vm.USER_ID).then(function (data) {
        vm.listTasksStates = data;
        if(vm.listTasksStates != null){
          vm.tasksStates.notStarted = vm.listTasksStates[0].toString();
          vm.tasksStates.inProgress = vm.listTasksStates[1].toString();
          vm.tasksStates.finished = vm.listTasksStates[2].toString();
          vm.tasksStates.breach = vm.listTasksStates[3].toString();
        }
      })
    }

    function getActiveTasksFn(){
      HomePageService.getActiveTasks(vm.USER_ID).then(successCallbackActiveTasks, errorCallbackActiveTasks);

      function successCallbackActiveTasks(data){

        vm.activeTasks = data;

        vm.uiState.activeTasks.loadGif = false;
        vm.uiState.activeTasks.showErrorPanel = false;
        if(vm.activeTasks.length > 0){
          vm.uiState.activeTasks.showTasks = true;
          vm.uiState.activeTasks.showNoActiveTasksPanel = false;
          setCssClassActiveTasks(vm.activeTasks)
        }
        else{
          vm.uiState.activeTasks.showTasks = false;
          vm.uiState.activeTasks.showNoActiveTasksPanel = true;
        }
      }

      function errorCallbackActiveTasks(){
        vm.uiState.activeTasks.loadGif = false;
        vm.uiState.activeTasks.showTasks = false;
        vm.uiState.activeTasks.showNoActiveTasksPanel = false;
        vm.uiState.activeTasks.showErrorPanel = true;
      }
    }

    function getUrgentProjectsFn(){
      HomePageService.getUrgentProjects(vm.USER_ID).then(successCallbackUrgentProjects, errorCallbackUrgentProjects);

      function successCallbackUrgentProjects(data) {

        vm.urgentProjects = data;
        console.log(vm.urgentProjects);
        vm.uiState.urgentProjects.loadGif = false;
        vm.uiState.urgentProjects.showErrorPanel = false;
        if(vm.urgentProjects.length > 0){
          vm.uiState.urgentProjects.showUrgentProjects = true;
          vm.uiState.urgentProjects.showNoUrgentProjectsPanel = false;
          setCssClassUrgentProjects(vm.urgentProjects);
        }
        else{
          vm.uiState.urgentProjects.showUrgentProjects = false;
          vm.uiState.urgentProjects.showNoUrgentProjectsPanel = true;
        }

      }

      function errorCallbackUrgentProjects(){
        vm.uiState.urgentProjects.loadGif = false;
        vm.uiState.urgentProjects.showUrgentProjects = false;
        vm.uiState.urgentProjects.showNoUrgentProjectsPanel = false;
        vm.uiState.urgentProjects.showErrorPanel = true;
      }

    }

    //helper function

    function setCssClassActiveTasks(activeTasks) {
      var size = activeTasks.length;
      // debugger;
      for(var i=0 ; i<size ; i++){
        if(activeTasks[i].state == 'IN_PROGRESS'){
          activeTasks[i].cssClass = 'label label-warning';
          activeTasks[i].state = 'IN PROGRESS';
        }
        else{
          activeTasks[i].cssClass = 'label label-info';
          activeTasks[i].state = 'NOT STARTED'
        }
        activeTasks[i].createdOn = dateMillisecondsToDate(activeTasks[i].createdOn);
        activeTasks[i].deadline = dateMillisecondsToDate(activeTasks[i].deadline);



      }
    }

    function setCssClassUrgentProjects(urgentProjects){
      var size = urgentProjects.length;
      console.log("setCssClassUrgentProjects");
      for(var i=0 ; i<size ; i++){
        var currProject = urgentProjects[i];
        currProject.createdOn = dateMillisecondsToDate(urgentProjects[i].createdOn);
        currProject.deadline = dateMillisecondsToDate(urgentProjects[i].deadline);

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
        else{
          currProject.cssClass = 'label label-danger';
          currProject.state = 'BREACH OF DEADLINE';
        }
        urgentProjects[i] = currProject;
      }
    }

    function dateMillisecondsToDate(milliseconds){

      var d = new Date(milliseconds);
      var month = parseInt(d.getMonth()) + 1;
      var minutes = d.getMinutes();
      if(minutes.toString().length == 1){
        minutes = '0' + minutes;
      }
      return d.getDate() + "." +
        month + "." +
        d.getFullYear() + " " +
        d.getHours() + ":" +
        minutes;

    }












  }

})(angular);
