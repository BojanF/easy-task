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
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('logout', {
      url: '/logout',
      controller: 'LogoutController',
      controllerAs: 'vm'
    });
  }

})(angular);
