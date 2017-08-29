/**
 * Created by Bojan on 8/24/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TaskDetailsController', TaskDetailsController);

  TaskDetailsController.$inject = ['$log', '$stateParams', 'TaskDetailsService'];

  /* @ngInject */
  function TaskDetailsController($log, $stateParams, TaskDetailsService) {
    var vm = this;
    vm.title = ' Teams that you are member of';
    vm.USER_ID = 1;
    vm.TASK_ID = 0;
    vm.task = {};
    vm.updatedTaskState = null;
    vm.updateToFinished = {
      required: false
    }
    vm.uiState = {
      showPage: true,
      showUpdate: false,
      showDetails: false,
      showError: false,
      loadGif: true,
      successMsg: null,
      errorMsg: null,
      updatingGif: false
    };
    vm.states = [];

    vm.projectDeadline = null;

    vm.updateTask = updateTaskFn;

    getTaskIdFn();

    function getTaskIdFn(){
      vm.TASK_ID = $stateParams.taskId;
      // vm.path=$location.absUrl();
      if (!/^\d+$/.test(vm.TASK_ID) || vm.TASK_ID.toString()=='0') {
        vm.uiState.showPage = false;
        console.log("OOOPS msg");
      }
      else{
        vm.uiState.showPage = true;
        console.log("show");
        getTaskFn();
      }



      console.log(vm.uiState.showPage);
    }

    function getTaskFn() {

      TaskDetailsService.getTask(vm.TASK_ID).then(

        function(data){
          //success callback

          vm.task = data;
          vm.uiState.loadGif = false;
          if(vm.task.id != undefined) {

            vm.uiState.showPage = true;
            vm.uiState.showDetails = true;
            vm.uiState.showError = false;
            if (vm.task.leader.user.id == vm.USER_ID)
              vm.uiState.showUpdate = true;
            else vm.uiState.showUpdate = false;

            taskParsing();

          }
          else{
            vm.uiState.showPage = false;
          }
        },

        function(){
          //error callback
          vm.uiState.loadGif = false;
          vm.uiState.showError = true;
          vm.uiState.showDetails = false;

        }
      );

    }

    function updateTaskFn(){

      if(vm.task.id != undefined && vm.uiState.showUpdate) {
        vm.uiState.updatingGif = true;

        vm.uiState.successMsg = null;
        vm.uiState.errorMsg = null;

        vm.task.state = vm.updatedTaskState.enum;
        vm.task.deadline = vm.task.deadline.toDate().getTime();

        if (vm.task.state == 'FINISHED') {

          vm.task.completedOn = vm.task.completedOn.toDate().getTime();
        }
        console.log(vm.task);

        TaskDetailsService.updateTask(vm.task).then(
          function (data) {
            //success callback
            vm.uiState.updatingGif = false;
            vm.task = data;
            taskParsing();
            vm.uiState.successMsg = "Successfully updated task \"" + vm.task.name + "\"";
          },
          function () {
            //error callback
            vm.uiState.updatingGif = false;
            vm.uiState.errorMsg = "Try again later!";
          }
        );
      }
    }

    //helper functions

    function taskParsing(){
      vm.task.createdOnString = dateMillisecondsToDate(vm.task.createdOn);
      vm.task.deadlineString = dateMillisecondsToDate(vm.task.deadline);
      vm.task.completedOnString = dateMillisecondsToDate(vm.task.completedOn);

      vm.task.createdOn = moment(new Date(vm.task.createdOn));
      vm.task.deadline = moment(new Date(vm.task.deadline));
      vm.projectDeadline = moment(new Date(vm.task.project.deadline));

      taskState(vm.task);
    }

    function taskState(currTask){

      // debugger;
      if(currTask.state == 'NOT_STARTED'){
        currTask.cssClass = 'label label-info';
        currTask.stateString = 'NOT STARTED';
        currTask.state = undefined;
        vm.states = [{'name':'Not started', 'enum': 'NOT_STARTED'}, {'name':'In progress', 'enum': 'IN_PROGRESS'}];
        vm.updatedTaskState = {'name':'Not started', 'enum': 'NOT_STARTED'};
      }
      else if(currTask.state == 'IN_PROGRESS'){
        currTask.cssClass = 'label label-warning';
        currTask.stateString = 'IN PROGRESS';
        currTask.state = undefined;
        vm.states = [{'name':'Not started', 'enum': 'NOT_STARTED'}, {'name':'In progress', 'enum': 'IN_PROGRESS'}, {'name':'Finished', 'enum': 'FINISHED'}];
        vm.updatedTaskState = {'name':'In progress', 'enum': 'IN_PROGRESS'};
      }
      else if(currTask.state == 'FINISHED'){
        currTask.cssClass = 'label label-success';
        currTask.stateString = 'FINISHED';
        currTask.state = undefined;
        vm.states = [{'name':'In progress', 'enum': 'IN_PROGRESS'}, {'name':'Finished', 'enum': 'FINISHED'}];
        vm.updatedTaskState = {'name':'Finished', 'enum': 'FINISHED'};
      }
      else{
        console.log("PROMASHAJ: " + currTask.state);
        currTask.cssClass = 'label label-danger';
        currTask.stateString = 'BREACH OF DEADLINE';
        currTask.state = undefined;
        vm.states = [{'name':'in progress', 'enum': 'IN_PROGRESS'}];

      }
    }


    function dateMillisecondsToDate(milliseconds){
      console.log(milliseconds);
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
