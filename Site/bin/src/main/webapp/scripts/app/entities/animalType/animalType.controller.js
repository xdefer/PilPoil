'use strict';

angular.module('pilPoilApp')
    .controller('AnimalTypeController', function ($scope, $state, AnimalType) {

        $scope.animalTypes = [];
        $scope.loadAll = function() {
            AnimalType.query(function(result) {
               $scope.animalTypes = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.animalType = {
                label: null,
                id: null
            };
        };
    });
