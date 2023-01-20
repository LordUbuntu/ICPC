#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

typedef long long ll;

struct Edge {
  int i, j;
  ll cap, flow, cost;
  Edge *rev;
  Edge(int ii, int jj, ll cc, ll ff, ll C) {
    i = ii;
    j = jj;
    cap = cc;
    flow = ff;
    cost = C;
    rev = NULL;
  }
};

struct MinCostMaxFlow {
  int n;
  vector<ll> ex;
  vector<vector<Edge*>> adj;

  MinCostMaxFlow(int NN) {
    n = NN;
    ex.resize(n);
    adj.resize(n);
  }

  void AddEdge(int i, int j, ll cap, ll cost) {
    Edge *forward = new Edge(i, j, cap, 0, cost);
    Edge *reverse = new Edge(j, i, 0, 0, -cost);
    forward->rev = reverse;
    reverse->rev = forward;
    adj[i].push_back(forward);
    adj[j].push_back(reverse);
  }

  pair<ll, ll> GetMaxFlow(int source, int sink) {
    vector<bool> canU;
    canU.resize(n);
    vector<bool> hasU;
    hasU.resize(n);
    vector<ll> dist;
    dist.resize(n);
    vector<ll> width;
    width.resize(n);
    vector<Edge*> prev;
    prev.resize(n);
    while(true) {
      for(int i = 0; i < n; i++) dist[i] = 1LL << 60;
      dist[source] = 0;
      width[source] = 1LL << 60;
      bool updated = hasU[source] = true;
      while(updated) {
        updated = false;
        for(int i = 0; i < n; hasU[i++] = false) {
          canU[i] = hasU[i];
        }
        for(int i = 0; i < n; i++) {
          if(canU[i]) {
            for(Edge* e : adj[i]) {
              if(e->flow != e->cap && dist[e->j] > dist[e->i] + e->cost) {
                dist[e->j] = dist[e->i] + (prev[e->j] = e)->cost;
                width[e->j] = min(width[e->i], e->cap - e->flow);
                hasU[e->j] = updated = true;
              }
            }
          }
        }
      }
      if(dist[sink] >= 1LL << 60) {
        break;
      }
      for(Edge* e = prev[sink]; e != NULL; e = prev[e->i]) {
        e->rev->flow = -(e->flow += width[sink]);
      }
    }
    ll flow = 0;
    ll cost = 0;
    for(Edge *e: adj[source]) {
      if(e->flow > 0) {
        flow += e->flow;
      }
    }
    for(int i = 0; i < n; i++) {
      for(Edge* e : adj[i]) {
        if(e->flow > 0 && e->i != sink && e->j != source) {
          cost += e->flow * e->cost;
        }
      }
    }
    return {flow, cost};
  }
};

int n;
pair<int, int> l[100];
int main() {
  cin >> n;
  for(int i = 0; i < n; i++) cin >> l[i].first >> l[i].second;
  sort(l, l+n);
  {
    MinCostMaxFlow mcmf(2*n+2);
    for(int i = 0; i < n; i++) {
      if(i < n-1) {
        mcmf.AddEdge(n+i, 2*n+1, 1, 0);
      }
      int play = l[i].second;
      if(i < n-1) {
        play--;
      }
      mcmf.AddEdge(2*n, i, play, 0);
      for(int j = 0; j < i; j++) {
        mcmf.AddEdge(i, j+n, 1, l[i].first ^ l[j].first);
      }
    }
    cout << mcmf.GetMaxFlow(2*n, 2*n+1).second << " ";
  }
  {
    MinCostMaxFlow mcmf(2*n+2);
    for(int i = 0; i < n; i++) {
      if(i < n-1) {
        mcmf.AddEdge(n+i, 2*n+1, 1, 0);
      }
      int play = l[i].second;
      if(i < n-1) {
        play--;
      }
      mcmf.AddEdge(2*n, i, play, 0);
      for(int j = 0; j < i; j++) {
        mcmf.AddEdge(i, j+n, 1, -(l[i].first ^ l[j].first));
      }
    }
    cout << -mcmf.GetMaxFlow(2*n, 2*n+1).second << endl;
  }
}
