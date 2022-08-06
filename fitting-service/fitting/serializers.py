from dataclasses import field
from rest_framework import serializers

from fitting.models import Fitting

class FittingListSerializer(serializers.ModelSerializer):
     class Meta:
        model = Fitting 
        fields = '__all__'
        
class FittingDetailSerializer(serializers.ModelSerializer):
      class Meta:
        model = Fitting 
        fields = '__all__'

class FittingPostSerializer(serializers.ModelSerializer):
      class Meta:
        model = Fitting 
        fields = ('userId', 'productId', 'image', 'desc',)

class FittingPostParamsSerializer(serializers.ModelSerializer):
      class Meta:
        model = Fitting 
        fields = ('userId', 'productId', 'desc',)

class FittingUpdateSerializer(serializers.ModelSerializer): 
      class Meta:
        model = Fitting 
        fields = ('desc',)
    
      