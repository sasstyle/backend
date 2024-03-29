# Generated by Django 4.0.6 on 2022-07-28 02:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('fitting', '0005_fitting_upd_date'),
    ]

    operations = [
        migrations.RenameField(
            model_name='fitting',
            old_name='pub_date',
            new_name='created_at',
        ),
        migrations.RenameField(
            model_name='fitting',
            old_name='upd_date',
            new_name='updated_at',
        ),
        migrations.AlterField(
            model_name='fitting',
            name='productId',
            field=models.FloatField(),
        ),
        migrations.AlterField(
            model_name='fitting',
            name='userId',
            field=models.CharField(max_length=100),
        ),
    ]
