'use strict';

angular.module('pilpoilApp')
    .controller('AdController', function ($scope, $state, Ad) {

        $scope.ads = [];
        $scope.loadAll = function() {
            Ad.query(function(result) {
               $scope.ads = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.ad = {
                description: null,
                recover: null,
                date: null,
                phone: null,
                email: null,
                adress: null,
                complement: null,
                postalCode: null,
                city: null,
                country: null,
                longitude: null,
                lattitude: null,
                id: null
            };
        };
    });
