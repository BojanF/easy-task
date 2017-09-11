/**
 * Created by Bojan on 8/7/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('StatsChartsController', StatsChartsController);

  StatsChartsController.$inject = ['$log', 'StatsChartsService','$cookies','$location'];

  /* @ngInject */
  function StatsChartsController($log, StatsChartsService,$cookies,$location) {
    var vm = this;

    //variables declaration

    vm.teamStats = [];
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
      },
      teams:{
        loadGif: true,
        showStats: false,
        showNoStatsPanel: false,
        errorPanel:false
      }
    };

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
      bar_chart:{
        points: [
          {"Team 1": 25},
          {"Team 2": 20},
          {"Team 3": 80}],
        columns: [{"id": "Team 1", "type": "bar"},
          {"id": "Team 2", "type": "bar"},
          {"id": "Team 3", "type": "bar"}]
      }
    };

    vm.entitiesData = {
      administratingProjects: [],
      tasksForAdminProjects: [],

      projectsStats: []
    };
    //functions declaration


    //functions invocation
    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getProjectStats();
      getTeamStats();
    }
    else {
      $location.path('/login');
    }



    //functions implementation


    function getProjectStats(){

      StatsChartsService.getProjectStats(vm.USER_ID).then(successProjects, errorProjects);

      function successProjects(data){
        console.log("Yes");
        var projectsStats = data;

        vm.uiState.donuts.projects.loadGif = false;
        vm.uiState.donuts.projects.showErrorPanel = false;
        if(projectsStats.length > 0){

          vm.c3DataTest.donuts.points.projects = [
            {"Created": projectsStats[0]},
            {"Not started": projectsStats[1]},
            {"In progress": projectsStats[2]},
            {"Up to date": projectsStats[3]},
            {"Finished": projectsStats[4]},
            {"Deadline braech": projectsStats[5]}
          ];

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

      function errorProjects(){
        console.log("No");
        vm.uiState.donuts.projects.loadGif = false;
        vm.uiState.donuts.projects.showStats = false;
        vm.uiState.donuts.projects.showNoStatsPanel = false;
        vm.uiState.donuts.projects.showErrorPanel = true;

        errorTasksPanel();
      }
    }

    function getTaskStats(){

      StatsChartsService.getTasksStats(vm.USER_ID).then(

        function(data){
          //success callback
          var states = data;

          vm.uiState.donuts.tasks.loadGif = false;

          vm.uiState.donuts.tasks.showErrorPanel = false;

          if(states.length > 0){
            vm.uiState.donuts.tasks.showStats = true;
            vm.uiState.donuts.tasks.showNoStatsPanel = false;
            vm.c3DataTest.donuts.points.tasks = [
              {"Not started": states[0]},
              {"In progress": states[1]},
              {"Finish": states[2]},
              {"Deadline braech": states[3]}
            ];
            console.log(vm.c3DataTest.donuts.points.tasks);
          }
          else{
            vm.uiState.donuts.tasks.showStats = false;
            vm.uiState.donuts.tasks.showNoStatsPanel = true;
          }
        },
        function(){
          //error callback
          errorTasksPanel();
        }
      );


    }

    function getTeamStats(){

      StatsChartsService.getTeamStats(vm.USER_ID).then(


        function (data){
          //success callback

          vm.teamStats = data;

          vm.uiState.teams.loadGif = false;

          if(vm.teamStats.length == 0){
            console.log("za ovoj user ne postojat timovi na koi e lider");
            vm.uiState.teams.showNoStatsPanel = true;
            vm.uiState.teams.showStats = false;
          }
          else{
            console.log("im timovi na koi e lider!");

            calculateStats(vm.teamStats);

            vm.uiState.teams.showNoStatsPanel = false;
            vm.uiState.teams.showStats = true;
            console.log(vm.c3DataTest.bar_chart);
          }

        },

        function(){
          //error callback
          console.log("leader error");
          vm.uiState.teams.loadGif = false;
          vm.uiState.teams.showNoStatsPanel = false;
          vm.uiState.teams.showStats = false;
          vm.uiState.teams.errorPanel = true;



        }
      );
    }

    //helper functions

    function calculateStats(teamStats){

      var size = teamStats.length;

      vm.c3DataTest.bar_chart.points = [];
      vm.c3DataTest.bar_chart.columns = [];
      var point = {};

      for(var i=0 ; i<size ; i++){
        var currTeam = teamStats[i];
        point[currTeam.name] = currTeam.projectNum;
        vm.c3DataTest.bar_chart.columns.push({"id":currTeam.name, "type":"bar"});

      }
      vm.c3DataTest.bar_chart.points.push(point);


    }

    function errorTasksPanel(){
      vm.uiState.donuts.tasks.loadGif = false;
      vm.uiState.donuts.tasks.showStats = false;
      vm.uiState.donuts.tasks.showNoStatsPanel = false;
      vm.uiState.donuts.tasks.showErrorPanel = true;
    }




  }

})(angular);

