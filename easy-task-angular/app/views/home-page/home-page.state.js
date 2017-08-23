/**
 * Created by Bojan on 8/7/2017.
 */

(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('home-page', {
      url: '',
      templateUrl: 'app/views/home-page/home-page.view.html',
      controller: 'HomePageController',
      controllerAs: 'vm'
    });
  }

})(angular);
