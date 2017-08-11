/**
 * Created by Bojan on 8/7/2017.
 */



(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('HomePageController', HomePageController);

  HomePageController.$inject = ['$log', 'HomePageService'];

  /* @ngInject */
  function HomePageController($log, HomePageService) {
    var vm = this;

    //variables declaration
    vm.USER_ID = 3;
    vm.user = null;
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
        showNoActiveTasksPanel: false
      },
      urgentProjects:{
        loadGif: true,
        showUrgentProjects: false,
        showNoUrgentProjectsPanel: false
      }
    }

    //functions declaration
    vm.getUser = getUserFn;
    vm.getTasksStates = getTasksStatesFn;
    vm.getActiveTasks = getActiveTasksFn;
    vm.getUrgentProjects = getUrgentProjectsFn;

    //functions invocation
    // getUserFn();
    getTasksStatesFn();
    getActiveTasksFn();
    getUrgentProjectsFn();

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
      HomePageService.getActiveTasks(vm.USER_ID).then(function (data) {

        vm.activeTasks = data;

        vm.uiState.activeTasks.loadGif = false;
        if(vm.activeTasks.length > 0){
          vm.uiState.activeTasks.showTasks = true;
          vm.uiState.activeTasks.showNoActiveTasksPanel = false;
          setCssClassActiveTasks(vm.activeTasks)
        }
        else{
          vm.uiState.activeTasks.showTasks = false;
          vm.uiState.activeTasks.showNoActiveTasksPanel = true;
        }
      })
    }

    function getUrgentProjectsFn(){
      HomePageService.getUrgentProjects(vm.USER_ID).then(function (data) {

        vm.urgentProjects = data;
        console.log(vm.urgentProjects);
        vm.uiState.urgentProjects.loadGif = false;
        if(vm.urgentProjects.length > 0){
          vm.uiState.urgentProjects.showUrgentProjects = true;
          vm.uiState.urgentProjects.showNoUrgentProjectsPanel = false;
          setCssClassUrgentProjects(vm.urgentProjects);
        }
        else{
          vm.uiState.urgentProjects.showUrgentProjects = false;
          vm.uiState.urgentProjects.showNoUrgentProjectsPanel = true;
        }

      })
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
        activeTasks[i].createdOnString = dateTimeToString(activeTasks[i].createdOn);
        activeTasks[i].deadlineString = dateTimeToString(activeTasks[i].deadline);



      }
    }

    function setCssClassUrgentProjects(urgentProjects){
      var size = urgentProjects.length;

      for(var i=0 ; i<size ; i++){
        urgentProjects[i].createdOnString = dateTimeToString(urgentProjects[i].createdOn);
        urgentProjects[i].deadlineString = dateTimeToString(urgentProjects[i].deadline);
      }
    }

    function dateTimeToString(dateTime){

      return dateTime.dayOfMonth + "." +
             dateTime.monthOfYear + "." +
             dateTime.year + " " +
             dateTime.hourOfDay + ":" +
             dateTime.minuteOfHour;

    }
    /*vm.save = save;
    vm.clear = clear;
    vm.edit = edit;
    vm.remove = remove;
    vm.entity = {};
    vm.courseEntities = [];
    vm.professors = [];
    vm.selectedProfessors = [];
    vm.saveOkMsg = null;
    vm.saveErrMsg = null;
    vm.uiState = {
      courses: {
        loadGif: true,
        showCourses: false,
        showNoCoursesPanel: false,
      },
      professors:{
        loadGif: true,
        showProfessors: false,
        showNoProfessorsPanel: false
      }
    }*/











  }

})(angular);
