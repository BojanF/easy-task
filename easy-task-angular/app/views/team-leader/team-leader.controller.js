/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('TeamLeaderController', TeamLeaderController);

  TeamLeaderController.$inject = ['$log', 'TeamLeaderService','$cookies','$location'];

  /* @ngInject */
  function TeamLeaderController($log, TeamLeaderService,$cookies,$location) {
    var vm = this;
    //variables declaration
    vm.teams = [];
    vm.uiState={
      loadGif: true,
      showTeams: false,
      showNoTeamsPanel: false,
      showErrorPanel: false,
      successDeleteTeam: null,
      errorDeleteTeam: null
    };

    //functions declaration
    vm.deleteTeam = deleteTeamFn;

    //functions invocation
    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getTeams();
    }
    else {
      $location.path('/login');
    }


    //functions implementation

    function getTeams(){

      vm.uiState.successDeleteTeam = null;
      vm.uiState.errorDeleteTeam = null;

      TeamLeaderService.getTeamStats(vm.USER_ID).then(

        function (data){
          vm.teams = data;

          vm.uiState.loadGif = false;
          vm.uiState.showErrorPanel = false;
          if(vm.teams.length > 0){

            vm.uiState.showTeams = true;
            vm.uiState.showNoTeamsPanel = false;

          }
          else{
            vm.uiState.showTeams = false;
            vm.uiState.showNoTeamsPanel = true;
          }
        },

        function (){
          vm.uiState.loadGif = false;
          vm.uiState.showTeams = false;
          vm.uiState.showNoTeamsPanel = false;
          vm.uiState.showErrorPanel = true;
        }
      );
    }

    function deleteTeamFn(teamId){

      vm.uiState.successDeleteTeam = null;
      vm.uiState.errorDeleteTeam = null;

      TeamLeaderService.deleteTeam(teamId).then(

        function(){
          //success callback
          console.log("SE IZBRISA");
          vm.uiState = {
            loadGif: true,
            showTeams: false,
            showNoTeamsPanel: false,
            showErrorPanel: false
          };
          getTeams();

          vm.uiState.successDeleteTeam = "Successfully deleted team!";

        },
        function(){
          //error callback
          var button = $(".removeTeam");

          setTimeout(function(){
            button.html('<i class="fa fa-times"></i>&nbsp; Delete team &nbsp;');
            $("#delete").removeAttr("disabled");
            $("#delete").removeAttr("id");

          }, 300);
          //console.log("NE SE IZBRISA");
          vm.uiState.errorDeleteTeam = " We run into an error! Try again later!";
        }

      );
    }

    //helper functions


  }

})(angular);
