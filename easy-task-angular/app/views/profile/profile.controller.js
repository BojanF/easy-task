/**
 * Created by Marijo on 10-Sep-17.
 */
/**
 * Created by Marijo on 02-Sep-17.
 */


(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('ProfileController', ProfileController);

  ProfileController.$inject = ['$log', '$rootScope', 'ProfileService','$location','bcrypt','$cookies'];

  /* @ngInject */
  function ProfileController($log, $rootScope, ProfileService,$location,$bcrypt,$cookies) {
    var vm = this;
    vm.user={};
    vm.pass={};
    vm.editEmail=editEmailFn;
    vm.changePass=changePassFn;
    vm.deactivate=deactivateFn;
    vm.passErrorMessage=false;
    vm.loadGif=true;
    getUserFn();
    vm.loadErr=false;
    vm.deactivateErr=false;

    function getUserFn(){
      vm.deactivateErr=false;
      $("#email_form").removeClass("has-error");
      $("#pass_form").removeClass("has-error");
      vm.passErrorMessage=false;
      $("#error").text("");
      vm.emailErrorMessage=false;

      if($cookies.get('id')) {
        var id=$cookies.get('id');
      }
      else {
        $location.path('/login');
      }
      ProfileService.findById(id).then(function(data){
      vm.user=data;
        vm.loadGif=false;
        vm.loadErr=false;

      },function(){
        vm.loadGif=false;
        vm.loadErr=true;
      })
    }

    function editEmailFn(){
      vm.deactivateErr=false;
     ProfileService.updateUser(vm.user).then(function(){
       vm.loadGif=true;

       $("#show_email").show();
       $("#change_email").hide();
       getUserFn();
     },function(){
      $("#email_form").addClass("has-error has-feedback");
       vm.emailErrorMessage=true;
     })
    }

    function changePassFn(){
      vm.deactivateErr=false;
      if(vm.pass.oldPass1==vm.pass.oldPass2)
      {

        $bcrypt.compare(vm.pass.oldPass1, vm.user.password, function(err, res) {
          if(res){
            vm.user.password=$bcrypt.hashSync(vm.pass.newPass,$bcrypt.genSaltSync(10));
            ProfileService.updateUser(vm.user).then(function(){
              vm.loadGif=true;
              $("#show_pass").show();
              $("#change_pass").hide();
              $("#old_pass_1").clear();
              $("#old_pass_2").clear();
              $("#new_pass").clear();
              getUserFn();
            },function(){
              $("#error").text(" We run into an error! Please try again.");
              $("#pass_form").addClass("has-error");
            })
          }
          else{
            console.log("incorrect");
            vm.passErrorMessage=true;
            $("#error").text("Incorrect password! Try again.");
            $("#pass_form").addClass("has-error");
          }
        });
      }else{
        vm.passErrorMessage=true
        $("#error").text(" Different passwords! Please try again. ");
        $("#pass_form").addClass("has-error");

      }
    }

    function deactivateFn(){
      vm.deactivateErr=false;
      vm.removeUser={};
      vm.removeUser.id=vm.user.id;
      vm.removeUser.username=$bcrypt.hashSync(vm.user.username,$bcrypt.genSaltSync(10));
      vm.removeUser.email=$bcrypt.hashSync(vm.user.email,$bcrypt.genSaltSync(10));
      vm.removeUser.password=vm.user.password;
      vm.removeUser.name=vm.user.name;
      vm.removeUser.surname=vm.user.surname;
      ProfileService.deactivate(vm.removeUser).then(function(){
        $cookies.remove('id');
        $cookies.remove('name');
        $location.path("/login").search('message','deactivate');
      },function(){
      vm.deactivateErr=true;
      })
    }
  }

})(angular);
