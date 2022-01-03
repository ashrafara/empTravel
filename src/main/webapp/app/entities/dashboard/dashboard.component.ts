import { Component } from '@angular/core';

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  public doughnutChartLabels: string[] = [' تجربة', 'تجربة', 'تجربة', 'تجربة'];
  public doughnutChartData: any;
  public reservationStateData: Array<any> = [];
  public doughnutChartType = 'doughnut';

  public pieChartLabels = ['شهر ٤', 'شهر ٣', 'شهر٢', 'شهر ١'];
  public pieChartData = [43, 54, 65, 33];

  public lineChartData: Array<any> = [
    {
      data: [65, 59, 80, 81, 56, 55, 40, 42, 43, 55, 60, 45],
      label: 'عدد التقارير',
    },
    // { data: [18, 48, 77, 9, 100, 27, 40], label: 'Series C' }
  ];
  public lineChartLabels: Array<any> = ['2019 Jun', '2019 Feb', '2019 Mar', '2019 Apr', '2019 May', '2019 Jun'];
  public showLegends = false;
  public lineChartOptions: any = {
    responsive: true,
    legend: {
      display: false,
    },

    scales: {
      xAxes: [
        {
          display: true,
          gridLines: {
            display: false,
          },
        },
      ],
      yAxes: [
        {
          display: true,
          gridLines: {
            display: false,
          },
        },
      ],
    },
  };
  public lineChartColors: Array<any> = [
    {
      // grey
      backgroundColor: 'rgb(19,124,183)',
      borderColor: '#2d95e5',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)',
    },
    // {
    //     // dark grey
    //     backgroundColor: 'rgba(77,83,96,0.2)',
    //     borderColor: 'rgba(77,83,96,1)',
    //     pointBackgroundColor: 'rgba(77,83,96,1)',
    //     pointBorderColor: '#fff',
    //     pointHoverBackgroundColor: '#fff',
    //     pointHoverBorderColor: 'rgba(77,83,96,1)'
    // },
    // {
    //     // grey
    //     backgroundColor: 'rgba(148,159,177,0.2)',
    //     borderColor: 'rgba(148,159,177,1)',
    //     pointBackgroundColor: 'rgba(148,159,177,1)',
    //     pointBorderColor: '#fff',
    //     pointHoverBackgroundColor: '#fff',
    //     pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    // }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';

  financialObserversCount: any;
  jehatCount: any;
  mm7ReportsCount: any;
  mh5OrdersCount: any;
}
