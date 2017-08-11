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
    vm.USER_ID = 3;
    vm.stateCode = 0;
    vm.tasks = [];
    vm.cssClassState = "";
    vm.taskState = "";
    vm.infoMessage = "Wrong state id!!!";
    vm.infoMessageCssClass = "panel panel-warning text-danger";
      vm.uiState = {
      loadGif: true,
      showTasks: false,
      showNoTasksPanel: false
    }

    //functions declaration
    vm.getTasks = getTasksFn;

    //functions invocation
    // getState();
    getTasksFn();

    //functions implementation
    function getState(){
      console.log("YESSSS");
      var searchObject = $location.search();
      // debugger;
      vm.stateCode = searchObject.state;

      if(vm.stateCode == 1) {
        vm.cssClassState = "label label-info";
        vm.taskState = "Tasks not started yet";
        vm.infoMessage = "You don`t have any tasks that are not started yet!";
        vm.infoMessageCssClass = "panel panel-warning text-success";
      }
      else if(vm.stateCode == 2) {
        vm.cssClassState = "label label-warning";
        vm.taskState = "Currently active tasks";
        vm.infoMessage = "You don`t have any tasks that you are currently working on!";
        vm.infoMessageCssClass = "panel panel-warning text-danger";
      }
      else if(vm.stateCode == 3) {
        vm.cssClassState = "label label-success";
        vm.taskState = "Finished tasks";
        vm.infoMessage = "You don`t have any finished tasks yet!";
        vm.infoMessageCssClass = "panel panel-warning text-danger";
      }
      else if(vm.stateCode == 4) {
        vm.cssClassState = "label label-danger";
        vm.taskState = "Breach of deadline tasks";
        vm.infoMessage = "You don`t have tasks with breached deadline";
        vm.infoMessageCssClass = "panel panel-warning text-success";
      }
      // else{
      //   vm.taskState = "";
      //   vm.infoMessageCssClass = "panel panel-warning text-danger";
      //   vm.infoMessage = "Wrong state id!!!";
      // }
    }

    function getTasksFn() {
      getState();
      TasksByStateService.getTasks(vm.USER_ID, vm.stateCode).then(function (data) {
        vm.tasks = data;

        vm.uiState.loadGif = false;
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
      })
    }

    //  helper functions

    function getPrintableDateFormat(tasks){
      var size = tasks.length;

      for(var i=0 ; i<size ; i++){
        // if(urgentProjects[i])
        tasks[i].createdOnString = dateTimeToString(tasks[i].createdOn);
        tasks[i].deadlineString = dateTimeToString(tasks[i].deadline);
      }
    }

    function dateTimeToString(dateTime){

      return dateTime.dayOfMonth + "." +
        dateTime.monthOfYear + "." +
        dateTime.year + " " +
        dateTime.hourOfDay + ":" +
        dateTime.minuteOfHour;

    }
  }

})(angular);
