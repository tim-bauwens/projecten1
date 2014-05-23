using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.Diagnostics;
using System.IO;
using GMap.NET.MapProviders;
using GMap.NET;
using GMap.NET.WindowsForms;
using GMap.NET.WindowsForms.Markers;
using Json;
using Newtonsoft.Json;

namespace ERClient
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();

            var url = "http://timbauwens1.ikdoeict.be/projecten1/getReportData.php?client=client";
            List<Report> emergencies = GetItems(url);
            comboBox1.SelectedIndex = 0;
            populateGridAddMarkers(emergencies);
        }

        public static List<Report> GetItems(string url)
        {
            // Syncronious Consumption
            var syncClient = new WebClient();
            string content = syncClient.DownloadString(url);
            return JsonConvert.DeserializeObject<List<Report>>(content);
        }

        private void load(object sender, EventArgs e)
        {
            gmap.MapProvider = GMap.NET.MapProviders.BingMapProvider.Instance;
            GMap.NET.GMaps.Instance.Mode = GMap.NET.AccessMode.ServerOnly;
            gmap.SetPositionByKeywords("Brussels, Belgium");

    
        }

        private void populateGridAddMarkers(List<Report> emergencies)
        {
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = emergencies;
            GMapOverlay markersOverlay = new GMapOverlay("markers");
            for (int i = 0; i < emergencies.Count; i++)
            {
                GMarkerGoogle marker = new GMarkerGoogle(new PointLatLng(emergencies[i].latitude, emergencies[i].longitude),
                GMarkerGoogleType.green);
                markersOverlay.Markers.Add(marker);
            }
            gmap.Overlays.Add(markersOverlay);
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void getSelectedRow(object sender, DataGridViewCellEventArgs e)
        {
            int hai = dataGridView1.CurrentCell.RowIndex;
        }
    }
}
