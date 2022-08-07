import reserve as reserve

from django.db import models

class Fitting(models.Model):
    userId = models.CharField(max_length=100)
    productId = models.FloatField()

    image = models.ImageField()
    desc = models.CharField(max_length=100, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateField(auto_now=True)

