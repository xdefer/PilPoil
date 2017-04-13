'use strict';

describe('Controller Tests', function() {

    describe('Animal Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAnimal, MockAd, MockAnimalType, MockBreed;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAnimal = jasmine.createSpy('MockAnimal');
            MockAd = jasmine.createSpy('MockAd');
            MockAnimalType = jasmine.createSpy('MockAnimalType');
            MockBreed = jasmine.createSpy('MockBreed');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Animal': MockAnimal,
                'Ad': MockAd,
                'AnimalType': MockAnimalType,
                'Breed': MockBreed
            };
            createController = function() {
                $injector.get('$controller')("AnimalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pilpoilApp:animalUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
