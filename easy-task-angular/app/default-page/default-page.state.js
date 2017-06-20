(function (angular) {
  'use strict';

  angular
    .module('easy-task-angular')
    .config(registerState);


  registerState.$inject = ['$stateProvider'];

  function registerState($stateProvider) {

    $stateProvider.state('defaultPage', {
      url: '/',
      templateUrl: 'app/default-page/default-page.view.html'
    });
  }

})(angular);
