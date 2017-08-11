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

    $stateProvider.state('stats-charts', {
      url: '/stats-charts',
      templateUrl: 'app/views/stats-charts/stats-charts.view.html',
      controller: 'StatsChartsController',
      controllerAs: 'vm'
    });
  }

})(angular);
