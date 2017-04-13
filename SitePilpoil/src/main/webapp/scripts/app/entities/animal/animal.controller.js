'use strict';

angular.module('pilpoilApp')
    .controller('AnimalController', function ($scope, $state, Animal) {

        $scope.animals = [];
        $scope.loadAll = function() {
            Animal.query(function(result) {
               $scope.animals = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.animal = {
                name: null,
                age: null,
                sexe: null,
                tatoo: null,
                chip: null,
                photo: null,
                colors: null,
                id: null
            };
        };
    });
