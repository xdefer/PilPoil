'use strict';

describe('Controller Tests', function() {

    describe('Ad Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAd, MockAdType, MockAnimal;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAd = jasmine.createSpy('MockAd');
            MockAdType = jasmine.createSpy('MockAdType');
            MockAnimal = jasmine.createSpy('MockAnimal');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Ad': MockAd,
                'AdType': MockAdType,
                'Animal': MockAnimal
            };
            createController = function() {
                $injector.get('$controller')("AdDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pilpoilwebApp:adUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
