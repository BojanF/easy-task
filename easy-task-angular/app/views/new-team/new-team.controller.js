/**
 * Created by Bojan on 8/8/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('NewTeamController', NewTeamController);

  NewTeamController.$inject = ['$log', 'NewTeamService','$cookies','$location'];

  /* @ngInject */
  function NewTeamController($log, NewTeamService,$cookies,$location) {
    //variables declaration

    var vm = this;


    vm.fetchedData = {
      leader: {},
      user: {},
      coworkers: [],
      userList: {}
    }

    vm.newTeam = {
      users: []
    };

    vm.uiState = {
      fetchingCoworkersMessage: '',
      loadGif: true,
      successNewTeam: null,
      errorNewTeam: null,
      canSubmit: false
    };
    // vm.coworkers = [{name: 'aco'}, {name: 'fia'}];

    //functions declaration
    vm.saveTeam = saveTeamFn;
    vm.clearNewTeam = clearNewTeamFn;
    vm.disableSubmit = disableSubmitFn;

    if($cookies.get('id')) {
      vm.USER_ID=$cookies.get('id');
      getCoworkers();
      getLeader();
    }
    else {
      $location.path('/login');
    }

    //functions invocation


    //functions implementation



    function getCoworkers(){

      NewTeamService.getCoworkers(vm.USER_ID).then(successCoworkers, errorCoworkers);

      function successCoworkers(data){
        vm.fetchedData.coworkers = data;
        vm.uiState.loadGif = false;
        if(vm.fetchedData.coworkers.length == 0){
          vm.uiState.fetchingCoworkersMessage = 'You don`t have coworkers';
        }
      }

      function errorCoworkers(){
        vm.uiState.fetchingCoworkersMessage = "Something went wrong! Reload the page!";
        vm.uiState.loadGif = false;
      }
    }

    function getLeader(){

      NewTeamService.getLeader(vm.USER_ID).then(successLeader, errorLeader);


      function successLeader(data){
        vm.fetchedData.leader = data;


        console.log(vm.fetchedData.leader);
        if(vm.fetchedData.leader.id == undefined){
          console.log("za ovoj user ne postoi leader");
          getUser();
        }
        else{
          console.log("lider postoi");

          vm.uiState.canSubmit = true;
        }

      }

      function errorLeader(){

        console.log("leader error");
        vm.uiState.canSubmit = false;
        vm.uiState.errorNewTeam = "Someting wrong happend! Reload the page!"


      }
    }

    function getUser(){

      NewTeamService.getUser(vm.USER_ID).then(successUser, errorUser);

      function successUser(data){
        console.log("user success");
        vm.fetchedData.user = data;
        saveNewLeader({user: vm.fetchedData.user});
      }

      function errorUser(){
        console.log("user error");

        vm.uiState.canSubmit = false;
      }
    }

    function saveNewLeader(newLeader){

      NewTeamService.saveNewLeader(newLeader).then(successNewLeader, errorNewLeader);

      function successNewLeader(data){

        vm.fetchedData.leader = data;
        if(vm.fetchedData.leader.id == undefined){
          vm.uiState.canSubmit = false;
          console.log("nesto neuspesno | create leader");
        }
        else{
          vm.uiState.canSubmit = true;
          console.log("uspesno kreiran leader !!!");

        }
      }

      function errorNewLeader(){

        vm.uiState.canSubmit = false;
        console.log("errorNewLeader");
        vm.uiState.errorNewTeam = "Something wrong happened reload the page!";
      }
    }

    function saveTeamFn(){
      $("#savingTeam").show();
      console.log(vm.newTeam);
      vm.uiState.successNewTeam = null;
      vm.uiState.errorNewTeam = null;

      // vm.newTeam.users.push(vm.fetchedData.userList);
      vm.newTeam.leader = vm.fetchedData.leader;
      vm.newTeam.users.push(vm.newTeam.leader.user);

      NewTeamService.saveNewTeam(vm.newTeam).then(successCallbackNewTeam, errorCallbackNewTeam);

      function successCallbackNewTeam(data){
        clearNewTeamFn();
        $("#savingTeam").hide();
        vm.uiState.successNewTeam = "Successfully created \"" + data.name + "\" team";
      }

      function errorCallbackNewTeam(){
        $("#savingTeam").hide();
        vm.uiState.errorNewTeam = "Something went wrong! Try again later!"
      }
    }

    function clearNewTeamFn(){
      vm.newTeam = {
        users: []
      };
    }

    //helper functions
    function disableSubmitFn(invalidForm){
      console.log("disable submit "+invalidForm);
      var result;
      if(invalidForm && vm.uiState.canSubmit)
        result = false;
      else if(invalidForm && !vm.uiState.canSubmit)
        result = false;
      else if(!invalidForm && vm.uiState.canSubmit)
        result = true;
      else result = false;

      console.log("REsult " + result);

      return result;
    }
  }

})(angular);
