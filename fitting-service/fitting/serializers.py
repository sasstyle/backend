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
        fields = ('userId', 'productId', 'desc',)

class FittingPostParamsSerializer(serializers.Serializer):
  userId = serializers.CharField()
  productId = serializers.IntegerField()
  clothUrl = serializers.URLField(max_length=None, min_length=None, allow_blank=False)
  profileUrl = serializers.URLField(max_length=None, min_length=None, allow_blank=False)
  desc = serializers.CharField()


class FittingUpdateSerializer(serializers.ModelSerializer): 
      class Meta:
        model = Fitting 
        fields = ('desc',)
    
      