/**
 * Created by Marijo on 02-Sep-17.
 */


(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .controller('LoginController', LoginController);

  LoginController.$inject = ['$log', '$rootScope', 'LoginService','$location','bcrypt','$cookies'];

  /* @ngInject */
  function LoginController($log, $rootScope,LoginService,$location,$bcrypt,$cookies) {

    $rootScope.location=$location;
    if($cookies.get("id")){
      $location.path("/");
    }
    $("#wrapper").hide();
    var vm = this;
    vm.login = loginFn;
    vm.register = registerFn;
    vm.clearUser = clearUserFn;
    vm.user={};
    vm.invalidLoginCredentials=false;
    vm.passwordsNotMatch=false;
    vm.registerError=false;
    vm.remember=false;
    vm.deactivateMessage=($location.search().message=='deactivate');
    vm.registerSuccessful=($location.search().message=='register');
    vm.logging=false;
    vm.registering=false;

    function loginFn(){
      vm.invalidLoginCredentials=false;
      vm.deactivateMessage=false;
      vm.registerSuccessful=false;
      vm.logging=true;
     LoginService.login(vm.user).then(function(data){
       vm.logging=false;
       if(data.name){
         $("#wrapper").show();
       $("#name_surname").html(" "+data.name+" "+data.surname);
       if(vm.remember){
         console.log("hello");
         $cookies.put("id",data.id,{'expires': new Date(3000,1,1)});
         $cookies.put("name",data.name+" "+data.surname,{'expires': new Date(3000,1,1)});
       }else{
         $cookies.put("id",data.id);
         $cookies.put("name",data.name+" "+data.surname);
       }

       $location.url("");
       }
       else{
         vm.invalidLoginCredentials=true;
       }
     },function(){
       vm.logging=false;
       vm.invalidLoginCredentials=true;
     });
    }

    function registerFn(){
      if(vm.user.password!=vm.confirmPassword){
        vm.passwordsNotMatch=true;
      }
      else {
        vm.invalidLoginCredentials=false;
        vm.deactivateMessage=false;
        vm.registerSuccessful=false;
        vm.registering=true;
        vm.user.name=vm.user.name.trim();
        vm.user.surname=vm.user.surname.trim();
        vm.user.email=vm.user.email.trim();
        vm.user.username=vm.user.username.trim();
        LoginService.register(vm.user).then(function(){
        vm.registering=false;
        vm.passwordsNotMatch=false;
        vm.registerError=false;
        vm.deactivateMessage=false;
        $location.path("/").search('message','register');
      },function(){
        vm.registering=false;
        vm.passwordsNotMatch=false;
        vm.registerError=true;
      });
      }
    }

    function clearUserFn(){
      vm.invalidLoginCredentials=false;
      vm.passwordsNotMatch=false;
      vm.registerError=false;
      vm.user=null;
      vm.confirmPassword=null;
    }



  }

})(angular);
