/**
 * Created by Bojan on 8/10/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TasksByStateController', TasksByStateController);

  TasksByStateController.$inject = ['$log', '$location', 'TasksByStateService'];

  /* @ngInject */
  function TasksByStateController($log, $location, TasksByStateService) {

    //variables declaration
    var vm = this;
    vm.title = 'easy-task';
    vm.USER_ID = 110;

    vm.tasks = [];
    vm.cssClassState = "";
    vm.taskState = "";
    vm.infoMessage = "Wrong state id!!!";
    vm.infoMessageCssClass = "panel panel-warning text-danger";
    vm.uiState = {
      loadGif: true,
      showTasks: false,
      showNoTasksPanel: false,
      showPage: false,
      showError: false
    }

    //functions declaration
    vm.getTasks = getTasksFn;

    //functions invocation
    getState();
    // getTasksFn();

    //functions implementation
    function getState(){
      console.log("YESSSS");
      var searchObject = $location.search();


      var stateCode = searchObject.state;

      if (!/^\d+$/.test(stateCode) || parseInt(stateCode)<1 || parseInt(stateCode)>4 ) {
        vm.uiState.showPage = false;
        console.log("OOOPS msg");
      }
      else {
        vm.uiState.showPage = true;
        if (stateCode == 1) {
          vm.cssClassState = "label label-info";
          vm.taskState = "Tasks not started yet";
          vm.infoMessage = "You don`t have any tasks that are not started yet!";
          vm.infoMessageCssClass = "panel panel-warning text-center text-success";
        }
        else if (stateCode == 2) {
          vm.cssClassState = "label label-warning";
          vm.taskState = "Currently active tasks";
          vm.infoMessage = "You don`t have any tasks that you are currently working on!";
          vm.infoMessageCssClass = "panel panel-warning text-center text-danger";
        }
        else if (stateCode == 3) {
          vm.cssClassState = "label label-success";
          vm.taskState = "Finished tasks";
          vm.infoMessage = "You don`t have any finished tasks yet!";
          vm.infoMessageCssClass = "panel panel-warning text-center text-danger";
        }
        else if (stateCode == 4) {
          vm.cssClassState = "label label-danger";
          vm.taskState = "Breach of deadline tasks";
          vm.infoMessage = "You don`t have tasks with breached deadline";
          vm.infoMessageCssClass = "panel panel-warning text-center text-success";
        }
        getTasksFn(stateCode)
      }

    }

    function getTasksFn(stateCode) {

      TasksByStateService.getTasks(vm.USER_ID, stateCode).then(successCallback, errorCallback);

      function successCallback(data) {
        vm.tasks = data;

        vm.uiState.loadGif = false;
        vm.uiState.showError = false;
        if(vm.tasks.length > 0){
          vm.uiState.showTasks = true;
          vm.uiState.showNoTasksPanel = false;
          getPrintableDateFormat(vm.tasks)
        }
        else{
          console.log("No tasks");
          vm.uiState.showTasks = false;
          vm.uiState.showNoTasksPanel = true;

        }
      }

      function errorCallback(){
        vm.uiState.loadGif = false;
        vm.uiState.showNoTasksPanel = false;
        vm.uiState.showTasks = false;
        vm.uiState.showError = true;
      }
    }

    //  helper functions

    function getPrintableDateFormat(tasks){
      var size = tasks.length;

      for(var i=0 ; i<size ; i++){
        tasks[i].createdOn = dateMillisecondsToDate(tasks[i].createdOn);
        tasks[i].deadline = dateMillisecondsToDate(tasks[i].deadline);
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
