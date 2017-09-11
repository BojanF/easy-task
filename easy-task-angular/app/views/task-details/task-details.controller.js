/**
 * Created by Bojan on 8/24/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TaskDetailsController', TaskDetailsController);

  TaskDetailsController.$inject = ['$log', '$scope', '$stateParams', 'TaskDetailsService', '$cookies', '$location'];

  /* @ngInject */
  function TaskDetailsController($log, $scope, $stateParams, TaskDetailsService, $cookies, $location) {
    var vm = this;
    vm.title = ' Teams that you are member of';
    vm.TASK_ID = 0;
    vm.task = {};
    vm.taskForUpdate = {};
    vm.updatedTaskState = {
      name: null,
      enum: null
    };
    vm.uiState = {
      showPage: true,
      showUpdate: false,
      showDetails: false,
      showError: false,
      loadGif: true,
      successMsg: null,
      errorMsg: null,
      updatingGif: false,
      completedOnPickerDisabled: true,
      updateStateOnly:{
        show: false,
        updatingGif: false,
        successMsg: false,
        errorMsg: false
      }
    };
    vm.eligibleTaskStates = [];
    vm.taskDeadlineMinDateRestriction = null;
    vm.projectDeadline = null;

    vm.datesRestrictions = {
      completedOn:{
        min: null,
        max: null
      },
      deadline:{
        min: null,
        max: null
      }
    };

    vm.taskDetails = taskDetailsFn;
    vm.updateTask = updateTaskFn;
    vm.changeState = changeStateFn;

    getTaskIdFn();

    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getTaskIdFn();
    }
    else {
      $location.path('/login');
    }

    function getTaskIdFn(){
      vm.TASK_ID = $stateParams.taskId;
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
            console.log(vm.task);
            var workersOnTaskIDs = [];
            for(var i=0 ; i<vm.task.users.length ; i++)
              workersOnTaskIDs.push(vm.task.users[i].id);

            if(workersOnTaskIDs.includes(vm.USER_ID) &&
              new Date() < vm.task.deadline &&
              vm.task.state!='BREACH_OF_DEADLINE' &&
              vm.task.leader.user.id!=vm.USER_ID) {
              vm.uiState.updateStateOnly.show = true;
            }

            vm.taskForUpdate = copyTask(vm.task);

            vm.uiState.showPage = true;
            vm.uiState.showDetails = true;
            vm.uiState.showError = false;
            if (vm.task.leader.user.id==vm.USER_ID) {
              vm.uiState.showUpdate = true;
              datesRestrictionFunc();
            }
            else{
              vm.uiState.showUpdate = false;
            }
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
      debugger;
      if(vm.task.id != undefined && vm.uiState.showUpdate) {
        vm.uiState.updatingGif = true;

        vm.uiState.successMsg = null;
        vm.uiState.errorMsg = null;

        vm.taskForUpdate.state = vm.updatedTaskState.enum;
        vm.taskForUpdate.deadline = vm.taskForUpdate.deadline.toDate().getTime();
        console.log(vm.taskForUpdate.deadline);
        if (vm.taskForUpdate.state == 'FINISHED') {
          vm.taskForUpdate.completedOn = vm.taskForUpdate.completedOn.toDate().getTime();
        }
        else{
          vm.taskForUpdate.completedOn = null;
        }

        TaskDetailsService.updateTask(vm.taskForUpdate).then(
          function (data) {
            //success callback
            vm.uiState.updatingGif = false;
            vm.task = data;
            vm.taskForUpdate = copyTask(vm.task);
            console.log(vm.taskForUpdate);
            datesRestrictionFunc();
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

    function taskDetailsFn(){
      // if (vm.task.leader.user.id==vm.USER_ID && vm.task.state!='FINISHED')
      //   vm.uiState.showUpdate = true;
      // else{
      //   vm.uiState.showUpdate = false;
      // }
    }

    function changeStateFn(){

      vm.uiState.updateStateOnly.successMsg = null;
      vm.uiState.updateStateOnly.errorMsg = null;

      if(vm.uiState.updateStateOnly.show) {
        vm.taskForUpdate.state = vm.updatedTaskState.enum;
        if (vm.taskForUpdate.state == 'FINISHED') {
          vm.taskForUpdate.completedOn = new Date().getTime();
        }
        else {
          vm.taskForUpdate.completedOn = null;
        }

        vm.uiState.updateStateOnly.updatingGif = true;
        TaskDetailsService.updateTask(vm.taskForUpdate).then(
          function (data) {
            //success callback
            vm.uiState.updateStateOnly.updatingGif = false;
            vm.uiState.updateStateOnly.successMsg = "State is successfully updated !";
            vm.task = data;
            vm.taskForUpdate = copyTask(vm.task);
            taskParsing();
            console.log("success update state");
          },
          function () {
            //error callback
            vm.uiState.updateStateOnly.updatingGif = false;
            vm.uiState.updateStateOnly.errorMsg = "Try again later !";
            console.log("error update state");
          }
        );
      }
    }



    //helper functions

    function datesRestrictionFunc(){
      //update task dates restriction
      // vm.datesRestrictions.completedOn.min = moment(new Date(vm.task.createdOn));
      vm.datesRestrictions.completedOn.min = moment(new Date());
      vm.datesRestrictions.completedOn.max = moment(new Date(vm.task.deadline));

      if(new Date() > vm.task.createdOn)
        vm.datesRestrictions.deadline.min = moment(new Date()).add(1, 'h');
      else
        vm.datesRestrictions.deadline.min = moment(new Date(vm.task.createdOn)).add(1, 'h');
      if(vm.task.state=='BREACH_OF_DEADLINE'){
        vm.taskForUpdate.deadline = null;
      }
      else{
        if(new Date() > vm.task.deadline)
          vm.taskForUpdate.deadline = null;
        else
          vm.taskForUpdate.deadline = vm.datesRestrictions.completedOn.max;
      }

      vm.datesRestrictions.deadline.max = moment(new Date(vm.task.project.deadline));
    }

    $scope.$watch('vm.updatedTaskState', function () {
      console.log("$scope: " );
      console.log(vm.updatedTaskState);
      console.log(vm.taskForUpdate);
      if(vm.updatedTaskState.enum == 'FINISHED'){
        vm.uiState.completedOnPickerDisabled = false;
      }
      else{
        vm.uiState.completedOnPickerDisabled = true;
      }
    }, true);

    function taskParsing(){
      vm.task.createdOnString = dateMillisecondsToDate(vm.task.createdOn);
      vm.task.deadlineString = dateMillisecondsToDate(vm.task.deadline);
      vm.task.completedOnString = dateMillisecondsToDate(vm.task.completedOn);

      if(vm.task.state == 'FINISHED')
        vm.taskForUpdate.completedOn = moment(new Date(vm.task.completedOn));
      // vm.taskForUpdate.deadline = moment(new Date(vm.task.deadline));
      // vm.projectDeadline = moment(new Date(vm.task.project.deadline));
      //tuka bese
      taskState(vm.task);
    }

    function taskState(currTask){

      // debugger;
      if(currTask.state == 'NOT_STARTED'){
        currTask.cssClass = 'label label-info';
        currTask.stateString = 'NOT STARTED';
        // currTask.state = undefined;
        // vm.eligibleTaskStates = [{'name': 'Not started', 'enum': 'NOT_STARTED'}, {'name': 'In progress', 'enum': 'IN_PROGRESS'}];
        // vm.updatedTaskState = {'name': 'Not started', 'enum': 'NOT_STARTED'};
        if(new Date() > currTask.createdOn) {
          vm.eligibleTaskStates = [{'name': 'Not started', 'enum': 'NOT_STARTED'}, {'name': 'In progress', 'enum': 'IN_PROGRESS'}];
          vm.updatedTaskState = {'name': 'Not started', 'enum': 'NOT_STARTED'};
        }
        else{
          vm.eligibleTaskStates = [{'name': 'Not started', 'enum': 'NOT_STARTED'}];
          vm.updatedTaskState = null;
        }
      }
      else if(currTask.state == 'IN_PROGRESS'){
        currTask.cssClass = 'label label-warning';
        currTask.stateString = 'IN PROGRESS';
        // currTask.state = undefined;
        vm.eligibleTaskStates = [{'name':'Not started', 'enum': 'NOT_STARTED'}, {'name':'In progress', 'enum': 'IN_PROGRESS'}, {'name':'Finished', 'enum': 'FINISHED'}];
        vm.updatedTaskState = {'name':'In progress', 'enum': 'IN_PROGRESS'};
      }
      else if(currTask.state == 'FINISHED'){
        currTask.cssClass = 'label label-success';
        currTask.stateString = 'FINISHED';
        // currTask.state = undefined;
        // vm.eligibleTaskStates = [{'name':'In progress', 'enum': 'IN_PROGRESS'}, {'name':'Finished', 'enum': 'FINISHED'}];
        vm.eligibleTaskStates = [{'name':'In progress', 'enum': 'IN_PROGRESS'}];
        // vm.updatedTaskState = {'name':'Finished', 'enum': 'FINISHED'};
        vm.updatedTaskState = null;

        if(vm.uiState.showUpdate)
          vm.taskForUpdate.deadline = null;
        //else

      }
      else{
        console.log("PROMASHAJ: " + currTask.state);
        currTask.cssClass = 'label label-danger';
        currTask.stateString = 'BREACH OF DEADLINE';
        // currTask.state = undefined;
        vm.eligibleTaskStates = [{'name':'in progress', 'enum': 'IN_PROGRESS'}];
        vm.updatedTaskState = null;
        vm.taskForUpdate.deadline = null;
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

    function copyTask(task){
      var copy = {};

      copy.id = task.id;
      copy.name = task.name;
      copy.description = task.description;
      copy.createdOn = task.createdOn;
      copy.completedOn = task.completedOn;
      copy.deadline = task.deadline;
      copy.state = task.state;
      copy.leader = task.leader;
      copy.project = task.project;
      copy.users = task.users;

      return copy;
    }
  }

})(angular);
