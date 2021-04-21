package fr.mi.wewok.domain

class FakeWokRepository
//@Inject constructor() : WokRepository
{
    /*override suspend fun getBases(): Resource<List<BasesModel>> {
        delay(1000)
        val bases = listOf(
            BasesModel("Nouilles de blé","img_base_1"),
            BasesModel("Nouilles de riz","img_base_2"),
            BasesModel("Riz Thaï","img_base_3"),
            BasesModel("Légumes sautés","img_base_4"),
            BasesModel("Nouilles Udon","img_base_5")
        )
        return Resource.success(bases)
    }

    override suspend fun getRetirerIngredients(): Resource<List<RetirerIngredientModel>> {
        delay(1000)
        val retirerIngredients = listOf(
            RetirerIngredientModel("Sans légumes","img_retier_ingredient_1"),
            RetirerIngredientModel("Sans pousse de soja","img_retier_ingredient_2"),
            RetirerIngredientModel("Sans oeuf","img_retier_ingredient_3")
        )
        return Resource.success(retirerIngredients)
    }

    override suspend fun getToppings(): Resource<List<ToppingsModel>> {
        delay(1000)
        val bases = listOf(
            ToppingsModel("Oignons frits","img_toppings_1",2F),
            ToppingsModel("Cacahuètes","img_toppings_2",3F),
            ToppingsModel("Coriandre","img_toppings_3",2.7F)
        )
        return Resource.success(bases)
    }

    override suspend fun getProteines(): Resource<List<ProteinesModel>> {
        delay(1000)
        val bases = listOf(
            ProteinesModel("Boeuf", "ic_boeuf",2F),
            ProteinesModel("Poulet","ic_tofu",3F),
            ProteinesModel("Tofu fumé","ic_tofu",2.7F),
            ProteinesModel("Lardinettes","ic_tofu",2.87F),
                ProteinesModel("Lardinettes","ic_tofu",2.87F),
                ProteinesModel("Lardinettes","ic_tofu",2.87F)
        )
        return Resource.success(bases)
    }

    override suspend fun getFromages(): Resource<List<FromagesModel>> {
        delay(1000)
        val bases = listOf(
            FromagesModel("Emental", "img_fromage_1",2F),
            FromagesModel("Feta","img_fromage_2",3F),
        )
        return Resource.success(bases)
    }

    override suspend fun getAutres(): Resource<List<AutresModel>> {
        delay(1000)
        val models = listOf(
            AutresModel("Emental", "img_fromage_1",2F),
            AutresModel("Feta","img_fromage_2",3F),
        )
        return Resource.success(models)
    }

    override suspend fun getWoks(): Resource<List<WoksModel>> {
        delay(1000)
        val list = listOf(
            WoksModel("Emental", "img_fromage_1","fake",2F),
            WoksModel("Feta","img_fromage_2","fake",3F),
        )
        return Resource.success(list)
    }

    override suspend fun getBoissons(): Resource<List<BoissonsModel>> {
        delay(1000)
        val models = listOf(
                BoissonsModel("Emental", "img_fromage_1",2F),
                BoissonsModel("Feta","img_fromage_2",3F),
        )
        return Resource.success(models)
    }

    override suspend fun getDesserts(): Resource<List<DessertsModel>> {
        delay(1000)
        val models = listOf (
            DessertsModel("Emental", "img_fromage_1",2F),
            DessertsModel("Feta","img_fromage_2",3F),
        )
        return Resource.success(models)
    }

    override suspend fun checkZone(zoneCode: String): Resource<ZoneModel> {
        return  return Resource.success(ZoneModel("","",""))
    }*/
}