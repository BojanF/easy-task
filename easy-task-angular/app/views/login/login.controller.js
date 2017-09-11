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
  function LoginController($log, $rootScope, LoginService,$location,$bcrypt,$cookies) {
    $rootScope.location=$location;
    if($cookies.get("id")){
      $location.path("/");
    }
    var vm = this;
    vm.login = loginFn;
    vm.register = registerFn;
    vm.clearUser = clearUserFn;
    vm.user={};
    vm.invalidLoginCredentials=false;
    vm.passwordsNotMatch=false;
    vm.registerError=false;
    vm.remember=false;

    function loginFn(){

     LoginService.login(vm.user).then(function(data){
       vm.invalidLoginCredentials=false;
       if(vm.remember){
         console.log("hello");
         $cookies.put("id",data.id,{'expires': new Date(3000,1,1)});
         $cookies.put("name",data.name+" "+data.surname,{'expires': new Date(3000,1,1)});
       }else{
         $cookies.put("id",data.id);
         $cookies.put("name",data.name+" "+data.surname);
       }

       $location.url("");
     },function(){
       vm.invalidLoginCredentials=true;
     });
    }

    function registerFn(){
      if(vm.user.password!=vm.confirmPassword){
        vm.passwordsNotMatch=true;
      }
      else {
      LoginService.register(vm.user).then(function(){
        vm.passwordsNotMatch=false;
        vm.registerError=false;
        $location.path("/");
      },function(){
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
