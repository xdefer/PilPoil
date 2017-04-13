'use strict';

angular.module('pilpoilApp').controller('AdDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ad', 'AdType', 'Animal',
        function($scope, $stateParams, $uibModalInstance, entity, Ad, AdType, Animal) {

        $scope.ad = entity;
        $scope.adtypes = AdType.query();
        $scope.animals = Animal.query();
        $scope.load = function(id) {
            Ad.get({id : id}, function(result) {
                $scope.ad = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('pilpoilApp:adUpdate', result);
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
