/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('ProjectDetailsController', ProjectDetailsController);

  ProjectDetailsController.$inject = ['$log', '$location', 'ProjectDetailsService'];

  /* @ngInject */
  function ProjectDetailsController($log, $location, ProjectDetailsService) {
    var vm = this;

    //variables declaration
    vm.title = 'project-details';
    vm.PROJECT_ID = 0;
    vm.USER_ID = 3;
    vm.c3DataTest = {
      donuts: {
        points: {
          tasks:[
            {"Not started": 0},
            {"In progress": 0},
            {"Finish": 0},
            {"Deadline braech": 0}
          ]
        },
        columns: {
          tasks:[
            {"id": "Not started", "type": "donut"},
            {"id": "In progress", "type": "donut"},
            {"id": "Finish", "type": "donut"},
            {"id": "Deadline braech", "type": "donut"}
          ]
        }
      }
    }

    vm.c3Data = {
      donuts: {
        points: {
          tasks:[]
        },
        columns: {
          tasks:[]
        }
      }
    }

    vm.project = {};
    vm.tasks = [];
    vm.comments = [];
    vm.documents = [];

    vm.uiState = {
      addNewTaskButton: false,
      addNewDocumentButton: false,
      tasks:{
        loadGif: true,
        showTasks: false,
        showNoTasksPanel: false
      },
      documents:{
        loadGif: true,
        showDocuments: false,
        showNoDocumentsPanel: false,
        loaded: false
      },
      comments:{
        loadGif: true,
        showComments: false,
        showNoCommentsPanel: false,
        loaded: false
      },
      showStats: false
    }

    //functions declaration
    vm.getProject = getProjectFn;
    vm.getProjectId = getProjectIdFn;
    vm.getTasks = getTasksFn;
    vm.getDocuments = getDocumentsFn;
    vm.getComments = getCommentsFn;
    vm.getStats = getStatsFn;

    //functions invocation
    getProjectFn();
    getTasksFn();
    //functions implementation


    function getProjectIdFn(){
      var searchObject = $location.search();
      vm.PROJECT_ID = searchObject.id;


    }

    function getProjectFn(){
      getProjectIdFn();
      ProjectDetailsService.getProject(vm.PROJECT_ID).then(function (data) {
        vm.project = data;

        if(vm.USER_ID == vm.project.team.leader.id.toString()){

          vm.uiState.addNewTaskButton = true;
          vm.uiState.addNewDocumentButton = true;
        }
      })
    }

    function getTasksFn(){
      ProjectDetailsService.getTasks(vm.PROJECT_ID).then(function (data) {
        vm.tasks = data;

        vm.uiState.tasks.loadGif = false;
        if(vm.tasks.length > 0){
          vm.uiState.tasks.showTasks = true;
          vm.uiState.tasks.showNoTasksPanel = false;
          threeInOneFunction(vm.tasks);
          vm.uiState.showStats = true;
        }
        else{
          vm.uiState.tasks.showTasks = false;
          vm.uiState.tasks.showNoTasksPanel = true;
        }
      })
    }



    function getDocumentsFn(){
      console.log("docs");

    }

    function getCommentsFn(){
      console.log("comments");
    }

    function getStatsFn() {
      console.log("stats");
    }


    //helper functions

    function threeInOneFunction(tasks){
      //set css class for task state
      //DateTime to string
      //calculate stats

      var size = tasks.length;
      var notStarted = 0;
      var inProgress = 0;
      var finished = 0;
      var breach  = 0;

      for(var i=0 ; i<size ; i++){
        var currTask = tasks[i];

        currTask.createdOnString = dateTimeToString(currTask.createdOn);
        currTask.deadlineString = dateTimeToString(currTask.deadline);

        if(currTask.state == 'NOT_STARTED'){
          currTask.cssClass = 'label label-info';
          currTask.state = 'NOT STARTED';
          notStarted++;
        }
        else if(currTask.state == 'IN_PROGRESS'){
          currTask.cssClass = 'label label-warning';
          currTask.state = 'IN PROGRESS';
          inProgress++;
        }
        else if(currTask.state == 'FINISHED'){
          currTask.cssClass = 'label label-success';
          finished++;
        }
        else{
          currTask.cssClass == 'label label-danger';
          currTask.state = 'BREACH OF DEADLINE';
          breach++;
        }

        tasks[i] = currTask;
      }

      vm.c3DataTest.donuts.points.tasks[0]["Not started"] = notStarted;
      vm.c3DataTest.donuts.points.tasks[1]["In progress"] = inProgress;
      vm.c3DataTest.donuts.points.tasks[2]["Finish"] = finished;
      vm.c3DataTest.donuts.points.tasks[3]["Deadline braech"] = breach;

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
