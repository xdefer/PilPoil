'use strict';

angular.module('pilpoilApp')
    .controller('AdTypeController', function ($scope, $state, AdType) {

        $scope.adTypes = [];
        $scope.loadAll = function() {
            AdType.query(function(result) {
               $scope.adTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.adType = {
                label: null,
                id: null
            };
        };
    });
