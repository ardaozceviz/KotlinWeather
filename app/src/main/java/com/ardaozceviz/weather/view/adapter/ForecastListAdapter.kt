package com.ardaozceviz.weather.view.adapter

/**
 * Created by arda on 07/11/2017.
 */
/*
class ForecastListAdapter(private val context: Context, private val forecastList: List<ListItem>) : RecyclerView.Adapter<ForecastListAdapter.WeatherInfoHolder>() {

    /*
    * Using Lambda function
    * to listen to click events
    * when any forecast item is clicked
    * */
    private var clickListener: (forecast: ListItem) -> Unit = {}

    override fun onBindViewHolder(holder: WeatherInfoHolder?, position: Int) {
        holder?.bindForecastItem(forecastList[position])

    }

    override fun getItemCount(): Int {
        Log.d("DaysListAdapter", "${forecastList.count()}")
        return forecastList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WeatherInfoHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.forecast_list_item, parent, false)
        return WeatherInfoHolder(view)
    }

    inner class WeatherInfoHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView?.setOnClickListener(this)
        }

        private val dayTextView = itemView?.findViewById<TextView>(R.id.list_item_day)
        private val iconImageView = itemView?.findViewById<ImageView>(R.id.list_item_image)
        //private val descriptionTextView = itemView?.findViewById<TextView>(R.id.list_item_description)
        private val temperatureTextView = itemView?.findViewById<TextView>(R.id.list_item_temperature)

        fun bindForecastItem(forecast: ListItem) {
            Log.d("DaysListAdapter", "bindForecastItem() forecast.dtTxt: ${forecast.dt}")
            var temperature = "NA"
            val condition = forecast.weather?.get(0)?.id
            //val description = forecast.weather?.get(0)?.description?.capitalize()
            //if (description != null) descriptionTextView?.text = description.capitalize()
            if (forecast.main != null) temperature = ForecastCommonMapper.calculateTemperature(forecast.main.temp)
            if (condition != null) {
                val iconName = ForecastCommonMapper.dayConditionToIcon(condition)
                val listItemImageResourceId = context.resources.getIdentifier(iconName, "drawable", context.packageName)
                iconImageView?.setImageResource(listItemImageResourceId)
            }
            dayTextView?.text = ForecastCommonMapper.getListItemDay(forecast.dt)
            temperatureTextView?.text = temperature
            //descriptionTextView?.text = description
        }

        override fun onClick(p0: View?) {
            clickListener(forecastList[adapterPosition])
        }
    }

    fun addOnclickListener(listener: (forecast: ListItem) -> Unit) {
        clickListener = listener
    }
}
        */