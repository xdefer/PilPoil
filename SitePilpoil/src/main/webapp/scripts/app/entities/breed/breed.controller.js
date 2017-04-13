'use strict';

angular.module('pilpoilApp')
    .controller('BreedController', function ($scope, $state, Breed) {

        $scope.breeds = [];
        $scope.loadAll = function() {
            Breed.query(function(result) {
               $scope.breeds = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.breed = {
                label: null,
                id: null
            };
        };
    });
