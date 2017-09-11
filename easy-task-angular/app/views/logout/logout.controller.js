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
    .controller('LogoutController', LogoutController);

  LogoutController.$inject = ['$location','$cookies'];

  /* @ngInject */
  function LogoutController($location,$cookies) {
    logoutFn();
    function logoutFn(){
        $cookies.remove("id");
        $cookies.remove("name");
        $location.path("/login");
    }


  }

})(angular);
