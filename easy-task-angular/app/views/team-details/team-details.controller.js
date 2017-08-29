/**
 * Created by Bojan on 8/28/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TeamDetailsController', TeamDetailsController);

  TeamDetailsController.$inject = ['$log', '$stateParams', 'TeamDetailsService'];

  /* @ngInject */
  function TeamDetailsController($log, $stateParams, TeamDetailsService) {
    var vm = this;

    //variables declaration
    vm.USER_ID = 1;
    vm.TEAM_ID = 0;
    vm.fetchedData = {
      team: {},
      coworkers: [],
      projects: []
    };

    vm.uiState = {
      showPage: true,
      leader: false,

      team: {
        loadGif: true,
        showData: false,
        showErrorPanel: false
      },
      stats:{
        loadGif: true,
        showStats: false,
        showNoStatsPanel: false,
        showErrorPanel: false
      },
      projects: {
        loadGif: true,
        showProjects: false,
        showNoProjects:false,
        showErrorPanel: false
      },
      update: {
        fetchingCoworkersMessage: '',
        loadGif: true,
        successUpdate: null,
        errorUpdate: null,
        updatingTeam: false
      }
    };

    vm.c3DataTest = {
      points:[
        {"Created": 25},
        {"Not started": 85},
        {"In progress": 80},
        {"Up to date": 35},
        {"Finished": 45},
        {"Deadline braech": 0}
      ],
      columns:[
        {"id": "Created", "type": "donut", "color": "#777"},
        {"id": "Not started", "type": "donut", "color": "#5bc0de"},
        {"id": "In progress", "type": "donut", "color": "#f0ad4e"},
        {"id": "Up to date", "type": "donut", "color": "#337ab7"},
        {"id": "Finished", "type": "donut", "color": "#5cb85c"},
        {"id": "Deadline braech", "type": "donut", "color": "#d9534f"}
      ]
    };

    //functions declaration
    vm.updateTeam = updateTeamFn;
    vm.getCoworkers = getCoworkersFn;
    vm.getTeamStats = getTeamStatsFn;

    //functions invocation
    getTeamId();

    //functions implementation
    function getTeamId(){
      vm.TEAM_ID = $stateParams.teamId;

      if (!/^\d+$/.test(vm.TEAM_ID) || vm.TEAM_ID.toString()=='0') {
        vm.uiState.showPage = false;
        console.log("OOOPS msg");
      }
      else{
        vm.uiState.showPage = true;
        console.log("show");
        getTeam(vm.TEAM_ID);

      }
      console.log(vm.uiState.showProject);
    }

    function getTeam(){

      TeamDetailsService.getTeam(vm.TEAM_ID).then(

        function(data){
          //success callback
          vm.fetchedData.team = data;

          vm.uiState.team.loadGif = false;
          vm.uiState.team.showData = true;
          vm.uiState.team.showErrorPanel = false;

          if(vm.fetchedData.team.id != undefined){
            vm.uiState.showPage = true;
            if(vm.fetchedData.team.leader.id == vm.USER_ID)
              vm.uiState.leader = true;
            getTeamProjects();
          }
          else{
            vm.uiState.showPage = false;

          }
        },
        function(){
          //error callback
          vm.uiState.team = {loadGif: false, showData: false, showErrorPanel: true};

          vm.uiState.projects = {loadGif: false, showProjects: false, showNoProjects: false, showErrorPanel: true};
        }

      );

    }

    function getTeamProjects(){
      TeamDetailsService.getProjectsForTeam(vm.TEAM_ID).then(


        function (data){
          //success callback
          vm.uiState.projects.loadGif = false;
          vm.uiState.projects.showErrorPanel = false;
          vm.fetchedData.projects = data;
          console.log(vm.fetchedData.projects);
          if(vm.fetchedData.projects.length > 0){
            vm.uiState.projects.showProjects = true;
            vm.uiState.projects.showNoProjects = false;
            setProjectsCssClassAndDates(vm.fetchedData.projects);
            vm.fetchedData.team.projectNum = vm.fetchedData.projects.length;



          }
          else{
            vm.uiState.projects.showProjects = false;
            vm.uiState.projects.showNoProjects = true;
            vm.fetchedData.team.projectNum = 0;



          }
        },

        function (){
          //error callback
          vm.uiState.projects = {loadGif: false, showProjects: false, showNoProjects: false, errorPanel: true};


        }

      );
    }

    function getTeamStatsFn(){
      console.log("STATS")
      TeamDetailsService.getTeamStats(vm.TEAM_ID).then(

        function(data){
          //success callback
          vm.uiState.stats.loadGif = false;
          vm.uiState.stats.showErrorPane = false;
          var teamStats = data;

          if(teamStats.length>0){
            vm.c3DataTest.points = [
              {"Created": teamStats[0]},
              {"Not started": teamStats[1]},
              {"In progress": teamStats[2]},
              {"Up to date": teamStats[3]},
              {"Finished": teamStats[4]},
              {"Deadline braech": teamStats[5]}
            ];
            vm.uiState.stats.showStats = true;
            vm.uiState.stats.showNoStatsPanel = false;
          }
          else{
            vm.uiState.stats.showStats = false;
            vm.uiState.stats.showNoStatsPanel = true;
          }
        },
        function(){
          //error callback
          vm.uiState.stats = {loadGif: false, showStats: false, showNoStatsPanel:false, showErrorPanel: true};
        }
      );
    }

    function getCoworkersFn(){

      TeamDetailsService.getCoworkers(vm.USER_ID).then(successCoworkers, errorCoworkers);

      function successCoworkers(data){
        vm.fetchedData.coworkers = data;
        vm.uiState.update.loadGif = false;
        if(vm.fetchedData.coworkers.length == 0){
          vm.uiState.update.fetchingCoworkersMessage = 'You don`t have coworkers';
        }
      }

      function errorCoworkers(){
        vm.uiState.update.fetchingCoworkersMessage = "Something went wrong! Reload the page!";
        vm.uiState.update.loadGif = false;
      }
    }

    function updateTeamFn(){
      vm.uiState.update.successUpdate = null;
      vm.uiState.update.errorUpdate = null;

      if(vm.fetchedData.team.id!=undefined && vm.uiState.leader) {
        vm.uiState.update.updatingTeam = true;
        console.log(vm.newTeam);




        var teamMembersIds = [];
        for(var i=0 ; i<vm.fetchedData.team.users.length ; i++)
          teamMembersIds.push(vm.fetchedData.team.users[i].id);
        if(!teamMembersIds.includes(vm.fetchedData.team.leader.user.id)){
          vm.fetchedData.team.users.push(vm.fetchedData.team.leader.user);
        }

        TeamDetailsService.updateTeam(vm.fetchedData.team).then(

          function (data) {

            vm.uiState.update.updatingTeam = false;
            vm.uiState.update.successUpdate = "Successfully updated \"" + data.name + "\" team";
          },

          function () {
            vm.uiState.update.updatingTeam = false;
            vm.uiState.update.errorUpdate = "Something went wrong! Try again later!"
          }
        );
      }
      else{
        vm.uiState.update.errorUpdate = "Refresh page and try again!";
      }
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
