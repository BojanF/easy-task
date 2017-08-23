/**
 * Created by Bojan on 8/7/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('StatsChartsController', StatsChartsController);

  StatsChartsController.$inject = ['$log', 'StatsChartsService'];

  /* @ngInject */
  function StatsChartsController($log, StatsChartsService) {
    var vm = this;

    //variables declaration
    vm.USER_ID = 1;

    vm.uiState = {
      donuts:{
        tasks: {
          loadGif: true,
          showStats: false,
          showNoStatsPanel: false,
          showErrorPanel: false
        },
        projects: {
          loadGif: true,
          showStats: false,
          showNoStatsPanel: false,
          showErrorPanel: false
        }
      }
    }

    vm.c3DataTest = {
      donuts: {
        points: {
          tasks:[
            {"Not started": 25},
            {"In progress": 20},
            {"Finish": 80},
            {"Deadline braech": 3}
          ],
          projects:[
            {"Created": 25},
            {"Not started": 85},
            {"In progress": 80},
            {"Up to date": 35},
            {"Finished": 45},
            {"Deadline braech": 0}
          ]
        },
        columns: {
          tasks:[
            {"id": "Not started", "type": "donut", "color": "#5bc0de"},
            {"id": "In progress", "type": "donut", "color": "#f0ad4e"},
            {"id": "Finish", "type": "donut", "color": "#5cb85c"},
            {"id": "Deadline braech", "type": "donut", "color": "#d9534f"}
          ],
          projects:[
            {"id": "Created", "type": "donut", "color": "#777"},
            {"id": "Not started", "type": "donut", "color": "#5bc0de"},
            {"id": "In progress", "type": "donut", "color": "#f0ad4e"},
            {"id": "Up to date", "type": "donut", "color": "#337ab7"},
            {"id": "Finished", "type": "donut", "color": "#5cb85c"},
            {"id": "Deadline braech", "type": "donut", "color": "#d9534f"}
          ]
        }
      },
      time_chart:{
        points: [
          {/*"x": "2017-01-01",*/ "Created": 30, "Not started": 0, "In progress": 0, "Up to date": 0, "Finished": 0, "Deadline braech": 0},
          {/*"x": "2017-01-02",*/ "Created": 5, "Not started": 10, "In progress": 10, "Up to date": 5, "Finished": 5, "Deadline braech": 0},
          {/*"x": "2017-01-03",*/ "Created": 3, "Not started": 20, "In progress": 45, "Up to date": 30, "Finished": 35, "Deadline braech": 1},
          {/*"x": "2017-01-04",*/ "Created": 10, "Not started": 15, "In progress": 70, "Up to date": 45, "Finished": 38, "Deadline braech": 0},
          {/*"x": "2017-01-05",*/ "Created": 18, "Not started": 25, "In progress": 60, "Up to date": 27, "Finished": 40, "Deadline braech": 2},
          {/*"x": "2017-01-06",*/ "Created": 25, "Not started": 20, "In progress": 85, "Up to date": 35, "Finished": 45, "Deadline braech": 0},
          {/*"x": "2017-01-06",*/ "Created": 25, "Not started": 20, "In progress": 85, "Up to date": 35, "Finished": 45, "Deadline braech": 0}
        ],
        columns: [
           // {"id": "x", "type": ""},
          {"id": "Created", "type": "spline", "color": "#777"},
          {"id": "Not started", "type": "spline", "color": "#5bc0de"},
          {"id": "In progress", "type": "spline", "color": "#f0ad4e"},
          {"id": "Up to date", "type": "spline", "color": "#337ab7"},
          {"id": "Finished", "type": "spline", "color": "#5cb85c"},
          {"id": "Deadline braech", "type": "spline", "color": "#d9534f"}
        ],
        tickValues: {"Created": "2017-01-01",
                      "Not started": "2017-01-02",
                      "In progress": "2017-01-03", "Up to date": "2017-01-04", "Finished": "2017-01-05", "Deadline braech": "2017-01-06"},

        tickValues2: [ "2017-01-01",
                       "2017-01-02",
                      "2017-01-03", "2017-01-04", "2017-01-05",  "2017-01-06"],
        proba: {"id": "x", "name": "My Data points"}
      }
    }

    vm.entitiesData = {
      administratingProjects: [],
      tasksForAdminProjects: []
    }
    //functions declaration


    //functions invocation
    getAdministratingProjects();

    //functions implementation
    function getAdministratingProjects(){
      console.log(vm.USER_ID);
      StatsChartsService.getAdministratingProjects(vm.USER_ID).then(successCallbackProjects, errorCallbackProjects);

      function successCallbackProjects(data){
        console.log("Yes");
        vm.entitiesData.administratingProjects = data;

        vm.uiState.donuts.projects.loadGif = false;
        vm.uiState.donuts.projects.showErrorPanel = false;
        if(vm.entitiesData.administratingProjects.length > 0){
          calculateStatsProjects(vm.entitiesData.administratingProjects);
          vm.uiState.donuts.projects.showStats = true;
          vm.uiState.donuts.projects.showNoStatsPanel = false;
          console.log(vm.c3DataTest.donuts.points.projects);

          getTaskStats();


        }
        else{
          vm.uiState.donuts.projects.showStats = false;
          vm.uiState.donuts.projects.showNoStatsPanel = true;
          vm.uiState.donuts.tasks.showNoStatsPanel = true;
          vm.uiState.donuts.tasks.loadGif = false;

        }
      }
      function errorCallbackProjects(){
        console.log("No");
        vm.uiState.donuts.projects.loadGif = false;
        vm.uiState.donuts.projects.showStats = false;
        vm.uiState.donuts.projects.showNoStatsPanel = false;
        vm.uiState.donuts.projects.showErrorPanel = true;

        errorTasksPanel();
      }
    }

    function getTaskStats(){

      StatsChartsService.getTaskStatesForProjects(vm.USER_ID).then(successTasks, errorTasks);

      function successTasks(data){

        var states = data;

        vm.uiState.donuts.tasks.loadGif = false;
        vm.uiState.donuts.tasks.showStats = true;
        vm.uiState.donuts.tasks.showNoStatsPanel = false;
        vm.uiState.donuts.tasks.showErrorPanel = false;

        vm.c3DataTest.donuts.points.tasks = [
                {"Not started": states[0]},
                {"In progress": states[1]},
                {"Finish": states[2]},
                {"Deadline braech": states[3]}
              ];
        console.log(vm.c3DataTest.donuts.points.tasks);
      }

      function errorTasks(){
        errorTasksPanel();
      }



    }


    function errorTasksPanel(){
      vm.uiState.donuts.tasks.loadGif = false;
      vm.uiState.donuts.tasks.showStats = false;
      vm.uiState.donuts.tasks.showNoStatsPanel = false;
      vm.uiState.donuts.tasks.showErrorPanel = true;
    }

    //helper functions

    function calculateStatsProjects(projects){
      //TODO da se presmetuva vo backend
      var size = projects.length;
      var created = 0;
      var notStarted = 0;
      var inProgress = 0;
      var upToDate = 0;
      var finished = 0;
      var breach = 0;

      for(var i=0 ; i<size ; i++){
        var currProject = projects[i];



        if(currProject.state == 'CREATED'){
          created++;
        }
        else if(currProject.state == 'NOT_STARTED'){
          notStarted++;
        }
        else if(currProject.state == 'IN_PROGRESS'){
          inProgress++;
        }
        else if(currProject.state == 'UP_TO_DATE'){
          upToDate++;
        }
        else if(currProject.state == 'FINISHED'){
          finished++;

        }
        else{
          breach++;
        }

        vm.c3DataTest.donuts.points.projects = [
          {"Created": created},
          {"Not started": notStarted},
          {"In progress": inProgress},
          {"Up to date": upToDate},
          {"Finished": finished},
          {"Deadline braech": breach}]
      }


    }
  }

})(angular);
