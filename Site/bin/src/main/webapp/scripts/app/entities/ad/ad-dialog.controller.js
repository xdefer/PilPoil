'use strict';

angular.module('pilPoilApp').controller('AdDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ad', 'AdType', 'Animal', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Ad, AdType, Animal, User) {

        $scope.ad = entity;
        $scope.adtypes = AdType.query();
        $scope.animals = Animal.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Ad.get({id : id}, function(result) {
                $scope.ad = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pilPoilApp:adUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ad.id != null) {
                Ad.update($scope.ad, onSaveSuccess, onSaveError);
            } else {
                Ad.save($scope.ad, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
